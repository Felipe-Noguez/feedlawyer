package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
}
