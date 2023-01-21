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
@Entity(name = "FUNCIONARIO_CARGO")
public class FuncionarioCargoPK {

    @Id
    @Column(name = "id_funcionario_cargo")
    private String idFuncionarioCargo;

    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "id_funcionario")
    private Integer idFuncionario;

}
