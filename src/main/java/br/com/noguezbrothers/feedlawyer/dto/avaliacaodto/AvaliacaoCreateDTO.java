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

    private String nomeAdvogado;

    private Double notaAvaliacao;

    private String nomeCliente;

    private String sugestao;

    private String emailCliente;

    private ServicoAvaliacaoDTO servicoAvaliacaoDTO;
}
