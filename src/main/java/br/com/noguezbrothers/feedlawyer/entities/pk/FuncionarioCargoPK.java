package br.com.noguezbrothers.feedlawyer.entities.pk;

import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
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
@Entity(name = "FUNCIONARIO_CARGO")
public class FuncionarioCargoPK {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNCIONARIO_CARGO_SEQUENCIA")
    @SequenceGenerator(name = "FUNCIONARIO_CARGO_SEQUENCIA", sequenceName = "SEQ_FUNCIONARIO_CARGO", allocationSize = 1)
    @Column(name = "id_funcionario_cargo")
    private String idFuncionarioCargo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo")
    private CargoEntity cargoEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private FuncionarioEntity funcionarioCargo;
}
