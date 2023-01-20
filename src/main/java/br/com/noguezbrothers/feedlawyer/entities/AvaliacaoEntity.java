package br.com.noguezbrothers.feedlawyer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "AVALIACAO")
public class AvaliacaoEntity {

    @Id
    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "nome_advogado")
    private String nomeAdvogado;

    @Column(name = "nota_avaliacao")
    private Double notaAvaliacao;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "sugestao")
    private String sugestao;

    @Column(name = "email_cliente")
    private String emailCliente;
}
