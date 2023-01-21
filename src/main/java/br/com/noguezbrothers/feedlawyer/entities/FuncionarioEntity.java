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
@Entity(name = "FUNCIONARIO")
public class FuncionarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNCIONARIO_SEQUENCIA")
    @SequenceGenerator(name = "FUNCIONARIO_SEQUENCIA", sequenceName = "SEQ_FUNCIONARIO", allocationSize = 1)
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
