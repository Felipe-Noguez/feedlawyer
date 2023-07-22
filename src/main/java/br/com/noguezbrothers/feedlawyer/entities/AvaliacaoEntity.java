package br.com.noguezbrothers.feedlawyer.entities;

import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "AVALIACAO")
public class AvaliacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AVALIACAO_SEQUENCIA")
    @SequenceGenerator(name = "AVALIACAO_SEQUENCIA", sequenceName = "SEQ_AVALIACAO", allocationSize = 1)
    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "nome_advogado")
    private String nomeAdvogado;

    @Column(name = "nota_avaliacao")
    private Double notaAvaliacao;

    @Column(name = "sugestao")
    private String sugestao;

    @Column(name = "situacao")
    @Enumerated(EnumType.ORDINAL)
    private Situacao situacao;

    @JsonIgnore
    @JoinColumn(name = "id_servico")
    @OneToOne(mappedBy = "avaliacaoEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ServicoEntity servicoAvaliacao;
}
