package br.com.noguezbrothers.feedlawyer.dto.funcionario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionarioDTO {

    private Integer idFuncionario;
    private String nomeFuncionario;
    private String cpf;
    private String especializacao;
    private String login;
    private String senha;
    private Integer tipoPerfil;
}
