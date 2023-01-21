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
@Entity(name = "CARGO")
public class CargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQUENCIA")
    @SequenceGenerator(name = "CARGO_SEQUENCIA", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "id_perfil")
    private Integer idPerfil;

    @Column(name = "nome")
    private String nome;
    
}
