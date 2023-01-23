package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioDTO;
import br.com.noguezbrothers.feedlawyer.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<FuncionarioDTO> cadastrarFuncionario(@Valid @RequestBody FuncionarioCreateDTO funcionarioCreateDTO) {
        return new ResponseEntity<>(funcionarioService.cadastrarFuncionario(funcionarioCreateDTO), HttpStatus.CREATED);
    }
}
