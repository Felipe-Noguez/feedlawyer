package br.com.noguezbrothers.feedlawyer.dto.funcionario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionarioCreateDTO {

    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String nomeFuncionario;
    @CPF(message = "CPF digitado não é válido, verifique e tente novamente.")
    private String cpf;
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String especializacao;
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String login;
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private String senha;
    @NotEmpty(message = "Campo obrigatório, verifique e tente novamente.")
    private Integer tipoPerfil;
}
