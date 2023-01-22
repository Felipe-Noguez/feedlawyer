package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {
}
