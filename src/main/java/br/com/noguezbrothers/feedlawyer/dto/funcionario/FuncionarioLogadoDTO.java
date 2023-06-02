package br.com.noguezbrothers.feedlawyer.dto.funcionario;

import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class FuncionarioLogadoDTO {

    private Integer idFuncionario;
    private String nomeFuncionario;
    private String cpf;
    private String especializacao;
    private String login;
    private Integer tipoPerfil;

    public FuncionarioLogadoDTO(FuncionarioEntity funcionarioEntity) {
        BeanUtils.copyProperties(funcionarioEntity, this);
    }
}
