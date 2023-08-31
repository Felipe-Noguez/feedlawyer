package br.com.noguezbrothers.feedlawyer.dto.usuario;

import br.com.noguezbrothers.feedlawyer.dto.usuariocargtodto.UsuarioCargoDTO;
import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {

    private Integer idUsuario;
    private String nomeUsuario;
    private String especializacao;
    private String email;
    private String login;
    private String cpf;
    private Situacao situacao;
    private Set<UsuarioCargoDTO> cargos;

    public UsuarioDTO(UsuarioEntity usuarioEntity) {
        BeanUtils.copyProperties(usuarioEntity, this);
    }

}
