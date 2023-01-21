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
@Entity(name = "FUNCIONARIO")
public class FuncionarioEntity {

    @Id
    @Column(name = "id_funcionario")
    private Integer idFuncionario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "especializacao")
    private String especialicazao;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;

}
