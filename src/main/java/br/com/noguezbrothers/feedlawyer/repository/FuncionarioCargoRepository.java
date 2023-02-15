package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.pk.FuncionarioCargoPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioCargoRepository extends JpaRepository<FuncionarioCargoPK, Integer> {

    Optional<FuncionarioCargoPK> findByCargoEntity_NomeContainingIgnoreCase(String nome);
}
