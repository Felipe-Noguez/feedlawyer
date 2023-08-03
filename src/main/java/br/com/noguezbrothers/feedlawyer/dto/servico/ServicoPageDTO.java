package br.com.noguezbrothers.feedlawyer.dto.servico;

import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoPageDTO {

    private Integer idServico;
    private String descricao;
    private Situacao situacao;
    private Optional<ServicoAvaDTO> servicoAvaDTO;

    public ServicoPageDTO(ServicoEntity servicoEntity) throws RegraDeNegocioException {
        this.idServico = servicoEntity.getIdServico();
        this.descricao = servicoEntity.getDescricao();
        this.situacao = servicoEntity.getSituacao();

        if(servicoEntity.getAvaliacaoEntity() != null) {
            this.servicoAvaDTO = Optional.of(new ServicoAvaDTO(
                    servicoEntity.getAvaliacaoEntity().getIdAvaliacao(),
                    servicoEntity.getAvaliacaoEntity().getDescricao(),
                    servicoEntity.getAvaliacaoEntity().getNomeAdvogado(),
                    servicoEntity.getAvaliacaoEntity().getNotaAvaliacao(),
                    servicoEntity.getAvaliacaoEntity().getServicoAvaliacao().getCliente().getNome(),
                    servicoEntity.getAvaliacaoEntity().getSugestao(),
                    servicoEntity.getAvaliacaoEntity().getServicoAvaliacao().getCliente().getEmail(),
                    servicoEntity.getAvaliacaoEntity().getSituacao()
            ));
        } else {
            this.servicoAvaDTO = Optional.empty();
        }
    }
}
