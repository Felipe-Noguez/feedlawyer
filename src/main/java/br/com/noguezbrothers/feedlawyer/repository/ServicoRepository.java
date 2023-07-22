package br.com.noguezbrothers.feedlawyer.repository;

import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoEntity, Integer> {

    @Query( " SELECT serv " +
            " FROM SERVICO serv " +
            " left join serv.avaliacaoEntity serav " +
            " inner join serv.servicoUsuarioPKS serupk " +
            " inner join serupk.usuarioEntityCliente usecl " +
            " WHERE (:idServico is null or :idServico = serv.idServico) " +
            " AND (:nomeCliente is null or UPPER(usecl.nome) LIKE UPPER(concat('%', :nomeCliente, '%') ) ) " +
            " AND (:descricao is null or UPPER(serv.descricao) LIKE UPPER(concat('%', :descricao, '%') ) ) " +
            " AND (:nomeAdvogado is null or UPPER(serav.nomeAdvogado) LIKE UPPER(concat('%', :nomeAdvogado, '%') ) ) " +
            " AND (:notaAvaliacao is null or :notaAvaliacao = serav.notaAvaliacao ) " +
            " AND (:situacao is null or serv.situacao = :situacao) " +
            "" )
    Page<ServicoEntity> listarServicos(String nomeCliente, Integer idServico, String descricao, String nomeAdvogado, Double notaAvaliacao, Situacao situacao, PageRequest pageRequest);
}
