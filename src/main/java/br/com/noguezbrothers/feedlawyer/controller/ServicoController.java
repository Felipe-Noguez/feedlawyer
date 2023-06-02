package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.service.ServicoService;
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
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> cadastrarServico(@RequestBody ServicoCreateDTO servicoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(servicoService.cadastrarServico(servicoCreateDTO), HttpStatus.CREATED);
    }
}
