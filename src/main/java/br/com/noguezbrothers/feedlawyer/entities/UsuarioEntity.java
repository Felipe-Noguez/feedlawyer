package br.com.noguezbrothers.feedlawyer.entities;

import br.com.noguezbrothers.feedlawyer.entities.pk.ServicoUsuarioPK;
import br.com.noguezbrothers.feedlawyer.entities.pk.UsuarioCargoPK;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "USUARIO")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQUENCIA")
    @SequenceGenerator(name = "USUARIO_SEQUENCIA", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "especializacao")
    private String especialicazao;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;

    @Column(name = "situacao")
    @Enumerated(EnumType.ORDINAL)
    private Situacao situacao;

    @OneToMany(mappedBy = "usuarioEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UsuarioCargoPK> usuarioCargoPKS;

    @OneToMany(mappedBy = "usuarioEntityFuncionario", fetch = FetchType.LAZY)
    private Set<ServicoUsuarioPK> servicoUsuarioFuncionario;

    @OneToMany(mappedBy = "usuarioEntityCliente", fetch = FetchType.LAZY)
    private Set<ServicoUsuarioPK> servicoUsuarioCliente;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuarioCargoPKS
                .stream()
                .map(UsuarioCargoPK::getCargoEntity)
                .collect(Collectors.toSet());
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
        return true;
    }
}
