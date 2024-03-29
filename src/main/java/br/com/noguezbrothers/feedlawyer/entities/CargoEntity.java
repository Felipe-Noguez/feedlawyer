package br.com.noguezbrothers.feedlawyer.entities;

import br.com.noguezbrothers.feedlawyer.entities.pk.UsuarioCargoPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "CARGO")
public class CargoEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARGO_SEQUENCIA")
    @SequenceGenerator(name = "CARGO_SEQUENCIA", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "id_perfil")
    private Integer idPerfil;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "cargoEntity", fetch = FetchType.LAZY)
    private Set<UsuarioCargoPK> usuarioCargoPKS;

    @Override
    public String getAuthority() {
        return nome;
    }
}
