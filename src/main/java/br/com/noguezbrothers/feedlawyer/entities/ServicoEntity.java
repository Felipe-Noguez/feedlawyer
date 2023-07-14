package br.com.noguezbrothers.feedlawyer.entities;

import br.com.noguezbrothers.feedlawyer.entities.pk.ServicoUsuarioPK;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;
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

    @Column(name = "situacao")
    @Enumerated(EnumType.ORDINAL)
    private Situacao situacao;

    @OneToMany(mappedBy = "servicoEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ServicoUsuarioPK> servicoUsuarioPKS;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_avaliacao")
    private AvaliacaoEntity avaliacaoEntity;

    public UsuarioEntity getCliente() throws RegraDeNegocioException {
        Optional<ServicoUsuarioPK> servicoUsuarioPKOptional = Optional.ofNullable(servicoUsuarioPKS.stream()
                .findFirst().orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado.")));

        UsuarioEntity usuarioEntityCliente = servicoUsuarioPKOptional.get().getUsuarioEntityCliente();
        return usuarioEntityCliente;
    }

    public UsuarioEntity getFuncionario() throws RegraDeNegocioException {
        Optional<ServicoUsuarioPK> servicoUsuarioPKOptional = Optional.ofNullable(servicoUsuarioPKS.stream()
                .findFirst().orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado.")));

        UsuarioEntity usuarioEntityCliente = servicoUsuarioPKOptional.get().getUsuarioEntityFuncionario();
        return usuarioEntityCliente;
    }
}
