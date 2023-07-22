package br.com.noguezbrothers.feedlawyer.dto.avaliacaodto;

import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.entities.AvaliacaoEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Situacao situacao;

    private ServicoDTO servicoDTO;

    public AvaliacaoDTO(AvaliacaoEntity avaliacaoEntity) throws RegraDeNegocioException {
        this.idAvaliacao = avaliacaoEntity.getIdAvaliacao();
        this.descricao = avaliacaoEntity.getDescricao();
        this.nomeAdvogado = avaliacaoEntity.getNomeAdvogado();
        this.notaAvaliacao = avaliacaoEntity.getNotaAvaliacao();
        this.nomeCliente = avaliacaoEntity.getServicoAvaliacao().getCliente().getNome();
        this.sugestao = avaliacaoEntity.getSugestao();
        this.emailCliente = avaliacaoEntity.getServicoAvaliacao().getCliente().getEmail();
        this.situacao = avaliacaoEntity.getSituacao();

        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setIdServico(avaliacaoEntity.getServicoAvaliacao().getIdServico());
        servicoDTO.setDescricao(avaliacaoEntity.getServicoAvaliacao().getDescricao());
        this.servicoDTO = servicoDTO;
    }
}
