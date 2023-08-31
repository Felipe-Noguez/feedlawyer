package br.com.noguezbrothers.feedlawyer.dto.usuario;

import br.com.noguezbrothers.feedlawyer.dto.usuariocargtodto.UsuarioCargoCreateDTO;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioCreateDTO {

    @Schema(description = "Nome do usuário", example = "Tom Sawyer")
    @NotBlank(message = "Campo obrigatório, verifique e tente novamente.")
    private String nomeUsuario;

    @Schema(description = "CPF do usuário", example = "12365185436")
    @CPF(message = "CPF digitado não é válido, verifique e tente novamente.")
    private String cpf;

    @Schema(description = "Especialidade do funcionario", example = "Advogado ...")
    @NotBlank(message = "Campo obrigatório, verifique e tente novamente.")
    private String especializacao;

    @Schema(description = "E-mail do usuário", example = "tom.sawyer@rush.com")
    @NotBlank(message = "Campo obrigatório, verifique e tente novamente.")
    private String email;

    @Schema(description = "Campo de login do usuário", example = "tom.sawyer")
    @NotBlank(message = "Campo obrigatório, verifique e tente novamente.")
    private String login;

    @Schema(description = "Senha do usuário", example = "123")
    @NotBlank(message = "Campo obrigatório, verifique e tente novamente.")
    private String senha;

    @Schema(description = "Situação de perfil do usuário", example = "ATIVO")
    @NotNull(message = "Campo obrigatório, verifique e tente novamente.")
    private Situacao situacao;

    private Set<UsuarioCargoCreateDTO> cargos;
}
