package br.com.noguezbrothers.feedlawyer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "SERVICO")
public class ServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICO_SEQUENCIA")
    @SequenceGenerator(name = "SERVICO_SEQUENCIA", sequenceName = "SEQ_SERVICO", allocationSize = 1)
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "descricao")
    private String descricao;

}
