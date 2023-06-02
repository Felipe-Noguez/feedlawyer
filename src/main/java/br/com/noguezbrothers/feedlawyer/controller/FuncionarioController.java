package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioDTO;
import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.service.AuthService;
import br.com.noguezbrothers.feedlawyer.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final AuthService authService;

    @PostMapping("/cadastrar")
    public ResponseEntity<FuncionarioDTO> cadastrarFuncionario(@Valid @RequestBody FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(funcionarioService.cadastrarFuncionario(funcionarioCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<PageDTO<FuncionarioDTO>> listarFuncionarios(@RequestParam(required = false) String nome,
                                                                      @RequestParam(required = false) String cpf,
                                                                      @RequestParam(required = false) String especializacao,
                                                                      @RequestParam(required = false) Integer idFuncionario,
                                                                      @RequestParam(required = false) Integer cargo,
                                                                      @RequestParam(required = false) Situacao situacao,
                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size) throws RegraDeNegocioException {
        return new ResponseEntity<>(funcionarioService.listarFuncionarios(nome, cpf, especializacao, idFuncionario, cargo, situacao, page, size), HttpStatus.OK);
    }

    @PutMapping("/atualizar/{cpf}")
    public ResponseEntity<FuncionarioDTO> atualizarFuncionario(@RequestBody FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        return  new ResponseEntity<>(funcionarioService.atualizarFuncionario(funcionarioCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/remover/{cpf}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable("cpf") String cpf) throws RegraDeNegocioException {
        funcionarioService.removerFuncionario(cpf);
        return ResponseEntity.noContent().build();
    }
}
