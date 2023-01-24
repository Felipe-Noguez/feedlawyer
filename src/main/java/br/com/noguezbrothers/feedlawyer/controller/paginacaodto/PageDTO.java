package br.com.noguezbrothers.feedlawyer.controller.paginacaodto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDTO<T> {

    private Long totalElementos;
    private Integer quantidadePaginas;
    private Integer pagina;
    private Integer tamanho;
    private List<T> elementos;
}