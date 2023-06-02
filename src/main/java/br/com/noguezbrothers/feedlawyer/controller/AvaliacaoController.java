package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.avaliacaodto.AvaliacaoDTO;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoDTO> cadastrarAvaliacao (@RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(avaliacaoService.cadastrarAvaliacao(avaliacaoCreateDTO), HttpStatus.CREATED);
    }
}
