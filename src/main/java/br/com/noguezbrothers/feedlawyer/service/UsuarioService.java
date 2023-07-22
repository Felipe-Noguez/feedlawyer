package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.dto.usuario.UsuarioCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.usuario.UsuarioDTO;
import br.com.noguezbrothers.feedlawyer.dto.usuario.UsuarioLogadoDTO;
import br.com.noguezbrothers.feedlawyer.dto.usuariocargtodto.UsuarioCargoDTO;
import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import br.com.noguezbrothers.feedlawyer.entities.UsuarioEntity;
import br.com.noguezbrothers.feedlawyer.entities.pk.UsuarioCargoPK;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoService cargoService;
    private final UsuarioCargoService usuarioCargoService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO cadastrarUsuario(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        Optional<UsuarioEntity> usuario = buscarPorLogin(usuarioCreateDTO.getLogin());
        if (usuario.isPresent()) {
            throw new RegraDeNegocioException("Login informado já cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(usuarioCreateDTO.getSenha());

        UsuarioEntity usuarioEntity = usuarioConverterEntity(usuarioCreateDTO);
        Set<UsuarioCargoPK> usuarioCargoPKS = new HashSet<>();

        usuarioEntity.setSenha(senhaCriptografada);
        usuarioEntity.setSituacao(Situacao.ATIVO);

        usuarioCreateDTO.getCargos().stream()
                .map(cargoEntity -> {
                            CargoEntity cargo = cargoService.buscarPorNomeCargo(cargoEntity.getNome());
                            UsuarioCargoPK usuarioCargoPK = new UsuarioCargoPK();
                            usuarioCargoPK.setCargoEntity(cargo);
                            usuarioCargoPK.setUsuarioEntity(usuarioEntity);
                            usuarioCargoPKS.add(usuarioCargoPK);

                            return cargo;
                        }
                )
                .collect(Collectors.toSet());

        usuarioEntity.setUsuarioCargoPKS(usuarioCargoPKS);

        usuarioRepository.save(usuarioEntity);

        return usuarioConvertDTO(usuarioEntity);
    }

    public PageDTO<UsuarioDTO> listarUsuarios(String nome, String cpf, String especializacao, Integer idUsuario, Integer cargo, Situacao situacao, Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Tamanho da página ou de elementos não podem ser menor que 0.");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UsuarioEntity> usuarioEntities = usuarioRepository.listarUsuario(nome, cpf, especializacao, idUsuario, cargo, situacao, pageRequest);

        List<UsuarioDTO> usuariosDTO = usuarioEntities.getContent()
                .stream()
                .map(this::usuarioConvertDTO)
                .collect(Collectors.toList());

        if (usuariosDTO.isEmpty()) {
            throw new RegraDeNegocioException("Dados não encontrados.");
        }

        return new PageDTO<>(usuarioEntities.getTotalElements(),
                usuarioEntities.getTotalPages(),
                page,
                size,
                usuariosDTO);
    }

    public UsuarioDTO atualizarUsuario(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = buscarUsuarioPorCpf(usuario.getCpf());
        usuarioEntity.setNome(usuario.getNome());
        usuarioEntity.setCpf(usuario.getCpf());
        usuarioEntity.setEspecialicazao(usuario.getEspecializacao());
        usuarioEntity.setLogin(usuario.getLogin());
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        new ArrayList<>();

        usuarioRepository.save(usuarioEntity);

        return usuarioConvertDTO(usuarioEntity);
    }

    public void removerUsuario(String cpf) throws RegraDeNegocioException {
        usuarioRepository.delete(buscarUsuarioPorCpf(cpf));
    }

    public UsuarioEntity buscarUsuarioPorCpf(String cpf) throws RegraDeNegocioException {
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado pelo CPF " + cpf));
    }

    public UsuarioEntity buscarPorIdUsuario(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado."));
    }

    public Optional<UsuarioEntity> buscarPorLogin(String login) {
        return usuarioRepository.findByLoginContainingIgnoreCase(login);
    }

    public Integer getIdLoggerdUser() throws RegraDeNegocioException {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public UsuarioLogadoDTO getLoggedUser() throws RegraDeNegocioException {
//        return objectMapper.convertValue(buscarPorIdUsuario(getIdLoggerdUser()), UsuarioLogadoDTO.class);
        return new UsuarioLogadoDTO(buscarPorIdUsuario(getIdLoggerdUser()));
    }

    private UsuarioEntity usuarioConverterEntity(UsuarioCreateDTO usuarioCreateDTO) {
        return new UsuarioEntity(null,
                usuarioCreateDTO.getNome(),
                usuarioCreateDTO.getCpf(),
                usuarioCreateDTO.getEspecializacao(),
                usuarioCreateDTO.getEmail(),
                usuarioCreateDTO.getLogin(),
                usuarioCreateDTO.getSenha(),
                usuarioCreateDTO.getSituacao(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>());
    }

    private UsuarioDTO usuarioConvertDTO(UsuarioEntity usuarioEntity) {
        Set<UsuarioCargoDTO> cargos = usuarioEntity.getUsuarioCargoPKS().stream()
                .map(cargo -> {
                    return usuarioCargoService.converterUsuarioCargoDTO(cargo);
                })
                .collect(Collectors.toSet());

        return new UsuarioDTO(usuarioEntity.getIdUsuario(),
                usuarioEntity.getNome(),
                usuarioEntity.getEspecialicazao(),
                usuarioEntity.getLogin(),
                usuarioEntity.getCpf(),
                usuarioEntity.getSituacao(),
                cargos
        );
    }
}
