package br.com.noguezbrothers.feedlawyer.dto.funcionario;

import br.com.noguezbrothers.feedlawyer.dto.funcionariocargtodto.FuncionarioCargoDTO;
import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionarioDTO {

    private Integer idFuncionario;
    private String nomeFuncionario;
    private String especializacao;
    private String login;
    private String cpf;
    private Situacao situacao;
    private Set<FuncionarioCargoDTO> cargos;

    public FuncionarioDTO(FuncionarioEntity funcionarioEntity) {
        BeanUtils.copyProperties(funcionarioEntity, this);
    }

}
