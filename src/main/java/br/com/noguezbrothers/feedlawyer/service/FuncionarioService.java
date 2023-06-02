package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioLogadoDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionariocargtodto.FuncionarioCargoDTO;
import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import br.com.noguezbrothers.feedlawyer.entities.pk.FuncionarioCargoPK;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.FuncionarioRepository;
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
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final CargoService cargoService;
    private final FuncionarioCargoService funcionarioCargoService;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioDTO cadastrarFuncionario(FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        Optional<FuncionarioEntity> funcionario = buscarPorLogin(funcionarioCreateDTO.getLogin());
        if (funcionario.isPresent()) {
            throw new RegraDeNegocioException("Login informado já cadastrado.");
        }

        String senhaCriptografada = passwordEncoder.encode(funcionarioCreateDTO.getSenha());

        FuncionarioEntity funcionarioEntity = funcionarioConverterEntity(funcionarioCreateDTO);
        Set<FuncionarioCargoPK> funcionarioCargoPKs = new HashSet<>();

        funcionarioEntity.setSenha(senhaCriptografada);
        funcionarioEntity.setSituacao(Situacao.ATIVO);

        funcionarioCreateDTO.getCargos().stream()
                .map(cargoEntity -> {
                            CargoEntity cargo = cargoService.buscarPorNomeCargo(cargoEntity.getNome());
                            FuncionarioCargoPK funcionarioCargoPK = new FuncionarioCargoPK();
                            funcionarioCargoPK.setCargoEntity(cargo);
                            funcionarioCargoPK.setFuncionarioCargo(funcionarioEntity);
                            funcionarioCargoPKs.add(funcionarioCargoPK);

                            return cargo;
                        }
                )
                .collect(Collectors.toSet());

        funcionarioEntity.setFuncionarioCargoPKS(funcionarioCargoPKs);

        funcionarioRepository.save(funcionarioEntity);

//        return new FuncionarioDTO(funcionarioEntity);
        return funcionarioConvertDTO(funcionarioEntity);
    }

    public PageDTO<FuncionarioDTO> listarFuncionarios(String nome, String cpf, String especializacao, Integer idFuncionario, Integer cargo, Situacao situacao, Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Tamanho da página ou de elementos não podem ser menor que 0.");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FuncionarioEntity> funcionarioEntities = funcionarioRepository.listarFuncionarios(nome, cpf, especializacao, idFuncionario, cargo, situacao, pageRequest);

        List<FuncionarioDTO> funcionariosDTO = funcionarioEntities.getContent()
                .stream()
                .map(this::funcionarioConvertDTO)
                .collect(Collectors.toList());

        if (funcionariosDTO.isEmpty()) {
            throw new RegraDeNegocioException("Dados não encontrados.");
        }

        return new PageDTO<>(funcionarioEntities.getTotalElements(),
                funcionarioEntities.getTotalPages(),
                page,
                size,
                funcionariosDTO);
    }

    public FuncionarioDTO atualizarFuncionario(FuncionarioCreateDTO funcionario) throws RegraDeNegocioException {
        FuncionarioEntity funcionarioEntity = buscarFuncionarioPorCpf(funcionario.getCpf());
        funcionarioEntity.setNome(funcionario.getFuncionario());
        funcionarioEntity.setCpf(funcionario.getCpf());
        funcionarioEntity.setEspecialicazao(funcionario.getEspecializacao());
        funcionarioEntity.setLogin(funcionario.getLogin());
        funcionarioEntity.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        new ArrayList<>();

        funcionarioRepository.save(funcionarioEntity);

        return funcionarioConvertDTO(funcionarioEntity);
    }

    public void removerFuncionario(String cpf) throws RegraDeNegocioException {
        funcionarioRepository.delete(buscarFuncionarioPorCpf(cpf));
    }

    public FuncionarioEntity buscarFuncionarioPorCpf(String cpf) throws RegraDeNegocioException {
        return funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado pelo CPF " + cpf));
    }

    public FuncionarioEntity buscarPorIdFuncionario(Integer idFuncionario) throws RegraDeNegocioException {
        return funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado."));
    }

    public Optional<FuncionarioEntity> buscarPorLogin(String login) {
        return funcionarioRepository.findByLoginContainingIgnoreCase(login);
    }

    public Integer getIdLoggerdUser() throws RegraDeNegocioException {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public FuncionarioLogadoDTO getLoggedUser() throws RegraDeNegocioException {
//        return objectMapper.convertValue(buscarPorIdFuncionario(getIdLoggerdUser()), FuncionarioLogadoDTO.class);
        return new FuncionarioLogadoDTO(buscarPorIdFuncionario(getIdLoggerdUser()));
    }

    private FuncionarioEntity funcionarioConverterEntity(FuncionarioCreateDTO funcionarioCreateDTO) {
        return new FuncionarioEntity(null,
                funcionarioCreateDTO.getFuncionario(),
                funcionarioCreateDTO.getCpf(),
                funcionarioCreateDTO.getEspecializacao(),
                funcionarioCreateDTO.getLogin(),
                funcionarioCreateDTO.getSenha(),
                funcionarioCreateDTO.getSituacao(),
                new HashSet<>(),
                new HashSet<>());
    }

    private FuncionarioDTO funcionarioConvertDTO(FuncionarioEntity funcionarioEntity) {
        Set<FuncionarioCargoDTO> cargos = funcionarioEntity.getFuncionarioCargoPKS().stream()
                .map(cargo -> {
                    return funcionarioCargoService.converterFuncionarioCargoDTO(cargo);
                })
                .collect(Collectors.toSet());

        return new FuncionarioDTO(funcionarioEntity.getIdFuncionario(),
                funcionarioEntity.getNome(),
                funcionarioEntity.getEspecialicazao(),
                funcionarioEntity.getLogin(),
                funcionarioEntity.getCpf(),
                funcionarioEntity.getSituacao(),
                cargos
        );
    }
}
