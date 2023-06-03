package br.com.noguezbrothers.feedlawyer.dto.usuario;

import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UsuarioLogadoDTO {

    private Integer idUsuario;
    private String nomeUsuario;
    private String cpf;
    private String especializacao;
    private String login;
    private Integer tipoPerfil;

    public UsuarioLogadoDTO(UsuarioEntity usuarioEntity) {
        BeanUtils.copyProperties(usuarioEntity, this);
    }
}
