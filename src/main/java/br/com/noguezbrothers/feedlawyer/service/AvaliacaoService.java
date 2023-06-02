package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.entities.AvaliacaoEntity;
import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ServicoService servicoService;
    private final ObjectMapper objectMapper;

    public AvaliacaoDTO cadastrarAvaliacao(AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = converterAvaliacaoEntity(avaliacaoCreateDTO);
        ServicoEntity servicoEntity = servicoService.getServicoEntityById(avaliacaoCreateDTO.getServicoAvaliacaoDTO().getIdServico());

        avaliacaoEntity.getServicoAvaliacao().setIdServico(servicoEntity.getIdServico());
        avaliacaoEntity.getServicoAvaliacao().setDescricao(servicoEntity.getDescricao());

        avaliacaoRepository.save(avaliacaoEntity);

        return converterAvaliacaoDTO(avaliacaoEntity);
    }

    public AvaliacaoEntity converterAvaliacaoEntity(AvaliacaoCreateDTO avaliacaoCreateDTO) {
        AvaliacaoEntity avaliacaoEntity = new AvaliacaoEntity();
        avaliacaoEntity.setDescricao(avaliacaoCreateDTO.getDescricao());
        avaliacaoEntity.setNomeAdvogado(avaliacaoCreateDTO.getNomeAdvogado());
        avaliacaoEntity.setNotaAvaliacao(avaliacaoCreateDTO.getNotaAvaliacao());
        avaliacaoEntity.setNomeCliente(avaliacaoCreateDTO.getNomeCliente());
        avaliacaoEntity.setSugestao(avaliacaoCreateDTO.getSugestao());
        avaliacaoEntity.setEmailCliente(avaliacaoCreateDTO.getEmailCliente());
        avaliacaoEntity.setServicoAvaliacao(objectMapper.convertValue(avaliacaoCreateDTO.getServicoAvaliacaoDTO(), ServicoEntity.class));

        return avaliacaoEntity;
    }

    public AvaliacaoDTO converterAvaliacaoDTO(AvaliacaoEntity avaliacaoEntity) {
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
        avaliacaoDTO.setIdAvaliacao(avaliacaoEntity.getIdAvaliacao());
        avaliacaoDTO.setDescricao(avaliacaoEntity.getDescricao());
        avaliacaoDTO.setNomeAdvogado(avaliacaoEntity.getNomeAdvogado());
        avaliacaoDTO.setNotaAvaliacao(avaliacaoEntity.getNotaAvaliacao());
        avaliacaoDTO.setNomeCliente(avaliacaoEntity.getNomeCliente());
        avaliacaoDTO.setSugestao(avaliacaoEntity.getSugestao());
        avaliacaoDTO.setEmailCliente(avaliacaoEntity.getEmailCliente());

        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setIdServico(avaliacaoEntity.getServicoAvaliacao().getIdServico());
        servicoDTO.setDescricao(avaliacaoEntity.getServicoAvaliacao().getDescricao());

        avaliacaoDTO.setServicoDTO(servicoDTO);

        return avaliacaoDTO;
    }
}
