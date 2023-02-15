package br.com.noguezbrothers.feedlawyer.security;

import br.com.noguezbrothers.feedlawyer.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final FuncionarioService funcionarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return funcionarioService.buscarPorLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Login inválido!"));
    }
}
