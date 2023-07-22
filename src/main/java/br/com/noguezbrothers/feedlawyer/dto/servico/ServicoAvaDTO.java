package br.com.noguezbrothers.feedlawyer.dto.servico;

import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoAvaDTO {

    private Integer idAvaliacao;

    private String descricao;

    private String nomeAdvogado;

    private Double notaAvaliacao;

    private String nomeCliente;

    private String sugestao;

    private String emailCliente;

    private Situacao situacao;
}
