package br.com.noguezbrothers.feedlawyer.dto.servico;

import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoAvaliacaoDTO {

    private Integer idServico;

    public ServicoAvaliacaoDTO(ServicoEntity servicoEntity) {
        BeanUtils.copyProperties(servicoEntity, this);
    }

}
