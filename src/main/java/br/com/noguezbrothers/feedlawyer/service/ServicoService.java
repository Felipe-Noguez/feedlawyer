package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoPageDTO;
import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import br.com.noguezbrothers.feedlawyer.entities.pk.ServicoUsuarioPK;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.exceptions.ServicoException;
import br.com.noguezbrothers.feedlawyer.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final UsuarioService usuarioService;

    public ServicoDTO cadastrarServico(ServicoCreateDTO servicoCreateDTO) throws RegraDeNegocioException {
        try {
            ServicoEntity servicoEntity = new ServicoEntity();
            ServicoUsuarioPK servicoUsuarioPK = new ServicoUsuarioPK();

            UsuarioEntity usuarioEntityFuncionario = usuarioService.buscarPorIdUsuario(usuarioService.getLoggedUser().getIdUsuario());
            UsuarioEntity usuarioEntityCliente = usuarioService.buscarPorIdUsuario(servicoCreateDTO.getIdCliente());

            servicoEntity.setDescricao(servicoCreateDTO.getDescricao());
            servicoEntity.setSituacao(Situacao.ATIVO);

            servicoUsuarioPK.setUsuarioEntityFuncionario(usuarioEntityFuncionario);
            servicoUsuarioPK.setUsuarioEntityCliente(usuarioEntityCliente);
            servicoUsuarioPK.setServicoEntity(servicoEntity);

            Set<ServicoUsuarioPK> usuariosServico = new HashSet<>();
            usuariosServico.add(servicoUsuarioPK);
            servicoEntity.setServicoUsuarioPKS(usuariosServico);

            servicoRepository.save(servicoEntity);

            return new ServicoDTO(servicoEntity);
        }catch (RegraDeNegocioException ex){
            throw new ServicoException("Erro ao cadastrar Serviço");
        }
    }

    public PageDTO<ServicoPageDTO> listarServicos(String nomeCliente, Integer idServico, String descricao, String nomeAdvogado, Double notaAvaliacao, Situacao situacao, Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Tamanho da página ou de elementos não podem ser menor que 0.");
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ServicoEntity> servicos = servicoRepository.listarServicos(nomeCliente, idServico, descricao, nomeAdvogado, notaAvaliacao, situacao, pageRequest);

        List<ServicoPageDTO> servicosDTO = servicos.getContent()
                .stream()
                .map(servicoEntity -> {
                    try {
                        return new ServicoPageDTO(servicoEntity);
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        if (servicosDTO.isEmpty()) {
            throw new RegraDeNegocioException("Dados não encontrados.");
        }

        return new PageDTO<>(servicos.getTotalElements(),
                servicos.getTotalPages(),
                page,
                size,
                servicosDTO);
    }

    public void desativarServico(Integer idServico) throws RegraDeNegocioException {
        ServicoEntity servico = buscarServicoEntityById(idServico);

        if(servico.getSituacao().equals(Situacao.INATIVO)) {
            throw new RegraDeNegocioException("Serviço já desativado");
        }

        servico.setSituacao(Situacao.INATIVO);

        servicoRepository.save(servico);
    }

    public void removerServico(Integer idServico) throws RegraDeNegocioException {
        servicoRepository.delete(buscarServicoEntityById(idServico));
    }

    public ServicoEntity buscarServicoEntityById(Integer idServico) throws RegraDeNegocioException {
        return servicoRepository.findById(idServico)
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado"));
    }

    public void save(ServicoEntity servicoEntity) {
        servicoRepository.save(servicoEntity);
    }
}
