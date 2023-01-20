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
@Entity(name = "CARGO")
public class CargoEntity {

    @Id
    @Column(name = "id_perfil")
    private Integer idPerfil;

    @Column(name = "nome")
    private String nome;
    
}
