package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {

    Page<FuncionarioEntity> findAllByNomeContainingIgnoreCaseOrNomeIsNullAndCpfOrCpfIsNullAndEspecialicazaoContainsIgnoreCaseOrEspecialicazaoIsNullAndTipoPerfilOrTipoPerfilIsNullAndIdFuncionarioOrIdFuncionarioIsNull(String nome, String cpf, String especializacao, Integer tipoPerfil, Integer idFuncionario, PageRequest pageRequest);
    Page<FuncionarioEntity> findAllByNomeIsNullOrNomeContainingIgnoreCaseAndCpfIsNullOrCpfAndEspecialicazaoIsNullOrEspecialicazaoContainsIgnoreCaseAndTipoPerfilIsNullOrTipoPerfilAndIdFuncionarioIsNullOrIdFuncionario(String nome, String cpf, String especializacao, Integer tipoPerfil, Integer idFuncionario, PageRequest pageRequest);

    @Query( "SELECT distinct func " +
            " FROM FUNCIONARIO func " +
            " WHERE (:nome is null or UPPER(func.nome) LIKE UPPER(concat('%', :nome, '%'))) " +
            " AND (:cpf is null or func.cpf LIKE concat('%', :cpf, '%')) " +
            " AND (:especializacao is null or UPPER(func.especialicazao) LIKE UPPER(concat('%',:especializacao, '%'))) " +
            " AND (:tipoPerfil is null or func.tipoPerfil = :tipoPerfil)" +
            " AND (:idFuncionario is null or :idFuncionario = func.idFuncionario)" +
            "")
    Page<FuncionarioEntity> listarFuncionarios(String nome, String cpf, String especializacao, Integer tipoPerfil, Integer idFuncionario, PageRequest pageRequest);
}
