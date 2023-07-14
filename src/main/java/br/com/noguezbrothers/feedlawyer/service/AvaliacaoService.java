package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoDTO;
import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.entities.AvaliacaoEntity;
import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.AvaliacaoException;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ServicoService servicoService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public AvaliacaoDTO cadastrarAvaliacao(AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        try {
            AvaliacaoEntity avaliacaoEntity = converterAvaliacaoEntity(avaliacaoCreateDTO);
            ServicoEntity servicoEntity = servicoService.getServicoEntityById(avaliacaoCreateDTO.getServicoAvaliacaoDTO().getIdServico());
            UsuarioEntity idUsuarioLogado = usuarioService.buscarPorIdUsuario(usuarioService.getLoggedUser().getIdUsuario());

            if (!servicoEntity.getCliente().getIdUsuario().equals(idUsuarioLogado.getIdUsuario())) {
                throw new RegraDeNegocioException("O serviço não pertence ao usuário atual.");
            }

            avaliacaoEntity.setSituacao(Situacao.ATIVO);
            avaliacaoEntity.setNomeAdvogado(servicoEntity.getFuncionario().getNome());
            avaliacaoEntity.setServicoAvaliacao(servicoEntity);

            AvaliacaoEntity avaliacao = avaliacaoRepository.save(avaliacaoEntity);

            servicoEntity.setAvaliacaoEntity(avaliacao);
            servicoService.save(servicoEntity);

            return converterAvaliacaoDTO(avaliacaoEntity);
        } catch (RegraDeNegocioException ex) {
            throw new AvaliacaoException("Erro ao cadastrar avaliação: " + ex.getMessage());
        }
    }

    public PageDTO<AvaliacaoDTO> listarAvaliacoes(String nomeCliente, Integer idAvaliacao, String descricao, String nomeAdvogado, Double notaAvaliacao, String sugestao, Situacao situacao, Integer page, Integer size) throws RegraDeNegocioException {
        try {
            if (page < 0 || size < 0) {
                throw new RegraDeNegocioException("Tamanho da página ou de elementos não podem ser menor que 0.");
            }

            PageRequest pageRequest = PageRequest.of(page, size);
            Page<AvaliacaoEntity> avaliacoes = avaliacaoRepository.listarAvaliacoes(nomeCliente, idAvaliacao, descricao, nomeAdvogado, notaAvaliacao, sugestao, situacao, pageRequest);

            List<AvaliacaoDTO> avaliacoesDTO = avaliacoes.getContent()
                    .stream()
                    .map(avaliacao -> objectMapper.convertValue(avaliacao, AvaliacaoDTO.class))
                    .collect(Collectors.toList());

            if (avaliacoesDTO.isEmpty()) {
                throw new RegraDeNegocioException("Dados não encontrados.");
            }

            return new PageDTO<>(avaliacoes.getTotalElements(),
                    avaliacoes.getTotalPages(),
                    page,
                    size,
                    avaliacoesDTO);

        } catch (AvaliacaoException ex) {
            throw new AvaliacaoException("Erro ao listar avaliações: " + ex.getMessage());
        }
    }

    public AvaliacaoEntity converterAvaliacaoEntity(AvaliacaoCreateDTO avaliacaoCreateDTO) {
        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        avaliacaoEntity.setDescricao(avaliacaoCreateDTO.getDescricao());
        avaliacaoEntity.setNotaAvaliacao(avaliacaoCreateDTO.getNotaAvaliacao());
        avaliacaoEntity.setSugestao(avaliacaoCreateDTO.getSugestao());

        return avaliacaoEntity;
    }

    public AvaliacaoDTO converterAvaliacaoDTO(AvaliacaoEntity avaliacaoEntity) throws RegraDeNegocioException {
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setIdAvaliacao(avaliacaoEntity.getIdAvaliacao());
        avaliacaoDTO.setDescricao(avaliacaoEntity.getDescricao());
        avaliacaoDTO.setNomeAdvogado(avaliacaoEntity.getNomeAdvogado());
        avaliacaoDTO.setNotaAvaliacao(avaliacaoEntity.getNotaAvaliacao());
        avaliacaoDTO.setNomeCliente(avaliacaoEntity.getServicoAvaliacao().getCliente().getNome());
        avaliacaoDTO.setSugestao(avaliacaoEntity.getSugestao());
        avaliacaoDTO.setEmailCliente(avaliacaoEntity.getServicoAvaliacao().getCliente().getEmail());

        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setIdServico(avaliacaoEntity.getServicoAvaliacao().getIdServico());
        servicoDTO.setDescricao(avaliacaoEntity.getServicoAvaliacao().getDescricao());

        avaliacaoDTO.setServicoDTO(servicoDTO);

        return avaliacaoDTO;
    }
}
