package br.com.noguezbrothers.feedlawyer.entities.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
@Entity(name = "SERVICO_FUNCIONARIO")
public class ServicoFuncionarioPK {

    @Id
    @Column(name = "id_servico_funcionario")
    private Integer idServicoFucionario;

    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "id_funcionario")
    private Integer idFuncionario;
}
