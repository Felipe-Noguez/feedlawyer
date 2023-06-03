package br.com.noguezbrothers.feedlawyer.entities.pk;

import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
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
@Entity(name = "SERVICO_USUARIO")
public class ServicoUsuarioPK {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICO_USUARIO_SEQUENCIA")
    @SequenceGenerator(name = "SERVICO_USUARIO_SEQUENCIA", sequenceName = "SEQ_SERVICO_USUARIO", allocationSize = 1)
    @Column(name = "id_servico_usuario")
    private Integer idServicoUsuario;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico")
    private ServicoEntity servicoEntity;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity servicoUsuario;
}
