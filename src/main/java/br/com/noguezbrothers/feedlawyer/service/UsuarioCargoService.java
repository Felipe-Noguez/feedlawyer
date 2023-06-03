package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.usuariocargtodto.UsuarioCargoDTO;
import br.com.noguezbrothers.feedlawyer.entities.pk.UsuarioCargoPK;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.UsuarioCargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioCargoService {
    private final UsuarioCargoRepository usuarioCargoRepository;

    public UsuarioCargoPK buscarUsuarioCargoPorNome(String nome) throws RegraDeNegocioException {
        return usuarioCargoRepository.findByCargoEntity_NomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Não encontrado pela intermediária nome " + nome));
    }

    public UsuarioCargoDTO converterUsuarioCargoDTO (UsuarioCargoPK usuarioCargoPK) {
        UsuarioCargoDTO usuarioCargoDTO = new UsuarioCargoDTO();
        usuarioCargoDTO.setIdCargo(usuarioCargoPK.getCargoEntity().getIdPerfil());
        usuarioCargoDTO.setNome(usuarioCargoPK.getCargoEntity().getNome());

        return usuarioCargoDTO;
    }

}
