package br.com.noguezbrothers.feedlawyer.dto.funcionario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionarioCreateDTO {

    @Schema(description = "Nome do usuário", example = "Tom Sawyer")
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String nomeFuncionario;

    @Schema(description = "CPF do usuário", example = "12365185436")
    @CPF(message = "CPF digitado não é válido, verifique e tente novamente.")
    private String cpf;

    @Schema(description = "Especialidade do funcionario", example = "Advogado ...")
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String especializacao;

    @Schema(description = "Campo de login do usuário", example = "tom.sawyer")
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String login;

    @Schema(description = "Senha do usuário", example = "123")
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String senha;

    @Schema(description = "Tipo de perfil do usuário", example = "1")
    @NotNull(message = "Campo obrigatório, verifique e tente novamente.")
    private Integer tipoPerfil;
}
