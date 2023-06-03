package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query( "SELECT distinct func " +
            " FROM USUARIO func " +
            " inner join func.usuarioCargoPKS fcar " +
            " WHERE (:nome is null or UPPER(func.nome) LIKE UPPER(concat('%', :nome, '%'))) " +
            " AND (:cpf is null or func.cpf LIKE concat('%', :cpf, '%')) " +
            " AND (:especializacao is null or UPPER(func.especialicazao) LIKE UPPER(concat('%',:especializacao, '%'))) " +
            " AND (:idUsuario is null or :idUsuario = func.idUsuario)" +
            " AND (:cargo is null or :cargo = fcar.cargoEntity.idPerfil ) " +
            " AND (:situacao is null or func.situacao = :situacao)" +
            "")
    Page<UsuarioEntity> listarUsuario(String nome, String cpf, String especializacao, Integer idUsuario, Integer cargo, Situacao situacao, PageRequest pageRequest);

    Optional<UsuarioEntity> findByCpf(String cpf);

    Optional<UsuarioEntity> findByLoginContainingIgnoreCase(String login);
}
