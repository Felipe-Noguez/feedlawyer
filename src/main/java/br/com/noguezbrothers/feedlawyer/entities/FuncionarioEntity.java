package br.com.noguezbrothers.feedlawyer.entities;

import br.com.noguezbrothers.feedlawyer.entities.pk.FuncionarioCargoPK;
import br.com.noguezbrothers.feedlawyer.entities.pk.ServicoFuncionarioPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    @OneToMany(mappedBy = "funcionarioCargo", fetch = FetchType.LAZY)
    private Set<FuncionarioCargoPK> funcionarioCargoPKS;

    @OneToMany(mappedBy = "servicoFuncionario", fetch = FetchType.LAZY)
    private Set<ServicoFuncionarioPK> servicoFuncionarioPKS;

}
