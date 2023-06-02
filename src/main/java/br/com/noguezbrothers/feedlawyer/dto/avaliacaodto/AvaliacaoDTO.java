package br.com.noguezbrothers.feedlawyer.dto.avaliacaodto;

import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.entities.AvaliacaoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvaliacaoDTO {

    private Integer idAvaliacao;

    private String descricao;

    private String nomeAdvogado;

    private Double notaAvaliacao;

    private String nomeCliente;

    private String sugestao;

    private String emailCliente;

    private ServicoDTO servicoDTO;

    public AvaliacaoDTO(AvaliacaoEntity avaliacaoEntity) {
        BeanUtils.copyProperties(avaliacaoEntity, this);
    }
}
