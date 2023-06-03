package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import br.com.noguezbrothers.feedlawyer.entities.pk.ServicoUsuarioPK;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final UsuarioService usuarioService;

    public ServicoDTO cadastrarServico(ServicoCreateDTO servicoCreateDTO) throws RegraDeNegocioException {
        ServicoEntity servicoEntity = new ServicoEntity();
        ServicoUsuarioPK servicoUsuarioPK = new ServicoUsuarioPK();
        UsuarioEntity usuarioEntity = usuarioService.buscarPorIdUsuario(usuarioService.getLoggedUser().getIdUsuario());

        servicoEntity.setDescricao(servicoCreateDTO.getDescricao());

        servicoUsuarioPK.setServicoUsuario(usuarioEntity);
        servicoUsuarioPK.setServicoEntity(servicoEntity);

        Set<ServicoUsuarioPK> usuarios = new HashSet<>();
        usuarios.add(servicoUsuarioPK);
        servicoEntity.setServicoUsuarioPKS(usuarios);

        servicoRepository.save(servicoEntity);

        ServicoDTO servicoDTO = new ServicoDTO(servicoEntity);
        return new ServicoDTO(servicoEntity);
    }

    public ServicoEntity getServicoEntityById(Integer idServico) throws RegraDeNegocioException {
        return servicoRepository.findById(idServico)
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado"));
    }
}
