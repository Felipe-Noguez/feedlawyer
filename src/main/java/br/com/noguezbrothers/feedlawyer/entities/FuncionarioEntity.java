package br.com.noguezbrothers.feedlawyer.entities;

import br.com.noguezbrothers.feedlawyer.entities.pk.FuncionarioCargoPK;
import br.com.noguezbrothers.feedlawyer.entities.pk.ServicoFuncionarioPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "FUNCIONARIO")
public class FuncionarioEntity implements UserDetails {

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

    @Column(name = "tipo_perfil")
    private Integer tipoPerfil;

    @OneToMany(mappedBy = "funcionarioCargo", fetch = FetchType.LAZY)
    private Set<FuncionarioCargoPK> funcionarioCargoPKS;

//    @OneToMany(mappedBy = "servicoFuncionario", fetch = FetchType.LAZY)
//    private Set<ServicoFuncionarioPK> servicoFuncionarioPKS;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "FUNCIONARIO_CARGO",
            joinColumns = @JoinColumn(name = "ID_FUNCIONARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CARGO")
    )
    private Set<CargoEntity> cargos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return cargos;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
