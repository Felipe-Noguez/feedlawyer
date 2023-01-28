package br.com.noguezbrothers.feedlawyer.dto.funcionario;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FuncionarioLogadoDTO {

    private Integer idFuncionario;
    private String nomeFuncionario;
    private String cpf;
    private String especializacao;
    private String login;
    private Integer tipoPerfil;
}
