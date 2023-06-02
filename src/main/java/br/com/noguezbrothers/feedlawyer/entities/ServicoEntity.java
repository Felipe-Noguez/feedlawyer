package br.com.noguezbrothers.feedlawyer.entities;

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
@Entity(name = "SERVICO")
public class ServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICO_SEQUENCIA")
    @SequenceGenerator(name = "SERVICO_SEQUENCIA", sequenceName = "SEQ_SERVICO", allocationSize = 1)
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "servicoEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ServicoFuncionarioPK> servicoFuncionarioPKS;


    @OneToOne(mappedBy = "servicoAvaliacao", fetch =  FetchType.LAZY)
    private AvaliacaoEntity avaliacaoEntity;

}
