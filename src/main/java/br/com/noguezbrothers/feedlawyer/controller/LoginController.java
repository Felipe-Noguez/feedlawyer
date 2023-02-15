package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.logindto.LoginDTO;
import br.com.noguezbrothers.feedlawyer.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/acessar")
    public String autenticar(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("Verificando autenticação . . .");
        return (authService.autenticarAcesso(loginDTO));
    }
}
