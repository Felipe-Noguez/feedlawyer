package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.AvaliacaoEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {

    @Query( " SELECT distinct aval " +
            " FROM AVALIACAO aval " +
            " inner join aval.servicoAvaliacao serav " +
            " inner join serav.servicoUsuarioPKS serupk " +
            " inner join serupk.usuarioEntityCliente usecl " +
            " WHERE (:idAvaliacao is null or :idAvaliacao = aval.idAvaliacao) " +
            " AND (:nomeCliente is null or UPPER(usecl.nome) LIKE UPPER(concat('%', :nomeCliente, '%') ) ) " +
            " AND (:descricao is null or UPPER(aval.descricao) LIKE UPPER(concat('%', :descricao, '%') ) ) " +
            " AND (:nomeAdvogado is null or UPPER(aval.nomeAdvogado) LIKE UPPER(concat('%', :nomeAdvogado, '%') ) ) " +
            " AND (:notaAvaliacao is null or :notaAvaliacao = aval.notaAvaliacao ) " +
            " AND (:sugestao is null or UPPER(aval.sugestao) LIKE UPPER(concat('%', :sugestao, '%') ) ) " +
            " AND (:situacao is null or aval.situacao = :situacao) " +
            "")
    Page<AvaliacaoEntity> listarAvaliacoes(String nomeCliente, Integer idAvaliacao, String descricao, String nomeAdvogado, Double notaAvaliacao, String sugestao, Situacao situacao, PageRequest pageRequest);
}
