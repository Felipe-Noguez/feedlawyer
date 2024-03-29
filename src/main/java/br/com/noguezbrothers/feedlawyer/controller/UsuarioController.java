package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.dto.usuario.UsuarioCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.usuario.UsuarioDTO;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.service.AuthService;
import br.com.noguezbrothers.feedlawyer.service.UsuarioService;
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
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthService authService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.cadastrarUsuario(usuarioCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<PageDTO<UsuarioDTO>> listarUsuarios(@RequestParam(required = false) String nome,
                                                                  @RequestParam(required = false) String cpf,
                                                                  @RequestParam(required = false) String especializacao,
                                                                  @RequestParam(required = false) Integer idUsuario,
                                                                  @RequestParam(required = false) Integer cargo,
                                                                  @RequestParam(required = false) Situacao situacao,
                                                                  @RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listarUsuarios(nome, cpf, especializacao, idUsuario, cargo, situacao, page, size), HttpStatus.OK);
    }

    @PutMapping("/atualizar/{cpf}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return  new ResponseEntity<>(usuarioService.atualizarUsuario(usuarioCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/remover/{cpf}")
    public ResponseEntity<Void> removerUsuario(@PathVariable("cpf") String cpf) throws RegraDeNegocioException {
        usuarioService.removerUsuario(cpf);
        return ResponseEntity.noContent().build();
    }
}
