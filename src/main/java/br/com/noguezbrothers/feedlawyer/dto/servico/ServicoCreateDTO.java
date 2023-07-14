package br.com.noguezbrothers.feedlawyer.dto.servico;

import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicoCreateDTO {

    @NotEmpty(message = "Campo com a avaliação não pode estar vazio.")
    @Schema(description = "Avaliação do serviço prestado. . .")
    private String descricao;

    private Integer idCliente;

    private AvaliacaoCreateDTO avaliacaoCreateDTO;
}
