package br.com.noguezbrothers.feedlawyer.entities.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
@Entity(name = "SERVICO_FUNCIONARIO")
public class ServicoFuncionarioPK {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICO_FUNCIONARIO_SEQUENCIA")
    @SequenceGenerator(name = "SERVICO_FUNCIONARIO_SEQUENCIA", sequenceName = "SEQ_SERVICO_FUNCIONARIO", allocationSize = 1)
    @Column(name = "id_servico_funcionario")
    private Integer idServicoFucionario;

    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "id_funcionario")
    private Integer idFuncionario;
}
