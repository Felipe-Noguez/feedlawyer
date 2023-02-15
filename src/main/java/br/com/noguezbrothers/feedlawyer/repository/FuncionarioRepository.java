package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {

    @Query( "SELECT distinct func " +
            " FROM FUNCIONARIO func " +
            " inner join func.funcionarioCargoPKS fcar " +
            " WHERE (:nome is null or UPPER(func.nome) LIKE UPPER(concat('%', :nome, '%'))) " +
            " AND (:cpf is null or func.cpf LIKE concat('%', :cpf, '%')) " +
            " AND (:especializacao is null or UPPER(func.especialicazao) LIKE UPPER(concat('%',:especializacao, '%'))) " +
            " AND (:idFuncionario is null or :idFuncionario = func.idFuncionario)" +
            " AND (:cargo is null or :cargo = fcar.cargoEntity.idPerfil ) " +
            " AND (:situacao is null or func.situacao = :situacao)" +
            "")
    Page<FuncionarioEntity> listarFuncionarios(String nome, String cpf, String especializacao, Integer idFuncionario, Integer cargo, Situacao situacao, PageRequest pageRequest);

    Optional<FuncionarioEntity> findByCpf(String cpf);

    Optional<FuncionarioEntity> findByLoginContainingIgnoreCase(String login);
}
