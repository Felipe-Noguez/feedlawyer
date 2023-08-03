package br.com.noguezbrothers.feedlawyer.entities.pk;

import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
@Entity(name = "USUARIO_CARGO")
public class UsuarioCargoPK {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_CARGO_SEQUENCIA")
    @SequenceGenerator(name = "USUARIO_CARGO_SEQUENCIA", sequenceName = "SEQ_USUARIO_CARGO", allocationSize = 1)
    @Column(name = "id_usuario_cargo")
    private Integer idUsuarioCargo;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo")
    private CargoEntity cargoEntity;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuarioEntity;
}
