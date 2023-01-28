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

    private final FuncionarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioService.buscarPorLogin(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email inválido!"));
    }
}
