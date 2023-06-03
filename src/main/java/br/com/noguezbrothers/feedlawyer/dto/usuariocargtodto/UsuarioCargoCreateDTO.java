package br.com.noguezbrothers.feedlawyer.dto.usuariocargtodto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioCargoCreateDTO {

    @Schema(description = "Tipo de cargo para permissão do usuário", example = "ROLE_ADMINISTRADOR")
    private String nome;

}
