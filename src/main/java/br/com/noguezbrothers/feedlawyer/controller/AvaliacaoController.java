package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoDTO;
import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<AvaliacaoDTO> cadastrarAvaliacao (@RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(avaliacaoService.cadastrarAvaliacao(avaliacaoCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<PageDTO<AvaliacaoDTO>> listarAvaliacoes(@RequestParam(required = false) String nomeCliente,
                                                              @RequestParam(required = false) Integer idAvaliacao,
                                                              @RequestParam(required = false) String descricao,
                                                              @RequestParam(required = false) String nomeAdvogado,
                                                              @RequestParam(required = false) Double notaAvaliacao,
                                                              @RequestParam(required = false) String sugestao,
                                                              @RequestParam(required = false) Situacao situacao,
                                                              @RequestParam(defaultValue = "0") Integer page,
                                                              @RequestParam(defaultValue = "10") Integer size) throws RegraDeNegocioException {
        return new ResponseEntity<>(avaliacaoService.listarAvaliacoes(nomeCliente, idAvaliacao, descricao, nomeAdvogado, notaAvaliacao, sugestao, situacao, page, size), HttpStatus.OK);
    }

//    @Override
    @PutMapping("/{idAvaliacao}/desativar")
    public ResponseEntity<Void> desativarAvaliacao(@PathVariable("idAvaliacao") Integer idAvaliacao) throws RegraDeNegocioException {

        avaliacaoService.desativarAvaliacao(idAvaliacao);

        return ResponseEntity.noContent().build();
    }

//    @Override
    @DeleteMapping("/{idAvaliacao}/remover")
    public ResponseEntity<Void> removerAvaliacao(@PathVariable("idAvaliacao") Integer idAvaliacao) throws RegraDeNegocioException {

        avaliacaoService.removerAvaliacao(idAvaliacao);

        return ResponseEntity.noContent().build();
    }
}
