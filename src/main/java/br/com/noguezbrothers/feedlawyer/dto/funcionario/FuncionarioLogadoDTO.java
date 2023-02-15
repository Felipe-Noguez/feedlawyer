package br.com.noguezbrothers.feedlawyer.dto.funcionario;

import lombok.Data;

@Data
public class FuncionarioLogadoDTO {

    private Integer idFuncionario;
    private String nomeFuncionario;
    private String cpf;
    private String especializacao;
    private String login;
    private Integer tipoPerfil;
}
