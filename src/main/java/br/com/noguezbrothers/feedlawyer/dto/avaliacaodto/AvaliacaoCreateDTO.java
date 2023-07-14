package br.com.noguezbrothers.feedlawyer.dto.avaliacaodto;

import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoAvaliacaoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvaliacaoCreateDTO {

    private String descricao;
    
    private Double notaAvaliacao;

    private String sugestao;

    private ServicoAvaliacaoDTO servicoAvaliacaoDTO;
}
