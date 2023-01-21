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
@Entity(name = "SERVICO")
public class ServicoEntity {

    @Id
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "descricao")
    private String descricao;

}
