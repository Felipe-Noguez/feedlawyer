package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.pk.UsuarioCargoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioCargoRepository extends JpaRepository<UsuarioCargoPK, Integer> {

    Optional<UsuarioCargoPK> findByCargoEntity_NomeContainingIgnoreCase(String nome);
}
