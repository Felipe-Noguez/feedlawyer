package br.com.noguezbrothers.feedlawyer.dto.servico;

import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoDTO {

    private Integer idServico;
    private String descricao;

    public ServicoDTO(ServicoEntity servicoEntity) {
        BeanUtils.copyProperties(servicoEntity, this);
    }
}
