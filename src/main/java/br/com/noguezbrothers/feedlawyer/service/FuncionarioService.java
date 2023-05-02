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
import br.com.noguezbrothers.feedlawyer.repository.CargoRepository;
import br.com.noguezbrothers.feedlawyer.repository.FuncionarioCargoRepository;
import br.com.noguezbrothers.feedlawyer.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final CargoRepository cargoRepository;
    private final FuncionarioCargoRepository funcionarioCargoRepository;
    private final CargoService cargoService;
    private final FuncionarioCargoService funcionarioCargoService;
    private final ObjectMapper objectMapper;

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

        return funcionarioConvertDTO(funcionarioEntity);
    }

//    public FuncionarioDTO cadastrarFuncionario(FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
//        Optional<FuncionarioEntity> funcionario = buscarPorLogin(funcionarioCreateDTO.getLogin());
//        if (funcionario.isPresent()) {
//            throw new RegraDeNegocioException("Login informado já cadastrado.");
//        }
//
//        String senhaCriptografada = passwordEncoder.encode(funcionarioCreateDTO.getSenha());
//
//        FuncionarioEntity funcionarioEntity = funcionarioConverterEntity(funcionarioCreateDTO);
//        Set<FuncionarioCargoPK> cargos = new HashSet<>();
//
//        funcionarioEntity.setSenha(senhaCriptografada);
//        funcionarioEntity.setSituacao(funcionarioCreateDTO.getSituacao());
//
//        for (FuncionarioCargoCreateDTO cargoDTO : funcionarioCreateDTO.getCargos()) {
//            CargoEntity cargoEntity = cargoService.buscarPorNomeCargo(cargoDTO.getNome());
//            FuncionarioCargoPK funcionarioCargo = new FuncionarioCargoPK();
//            funcionarioCargo.setCargoEntity(cargoEntity);
//            funcionarioCargo.setFuncionarioCargo(funcionarioEntity);
//
//            funcionarioEntity.getFuncionarioCargoPKS().add(funcionarioCargo);
//            cargos.add(funcionarioCargo);
//        }
//
//        funcionarioEntity.setFuncionarioCargoPKS(cargos);
//        funcionarioRepository.save(funcionarioEntity);
//
//        return funcionarioConvertDTO(funcionarioEntity);
//    }

//    public FuncionarioDTO cadastrarFuncionario(FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
//        Optional<FuncionarioEntity> funcionario = buscarPorLogin(funcionarioCreateDTO.getLogin());
//        if (funcionario.isPresent()) {
//            throw new RegraDeNegocioException("Login informado já cadastrado.");
//        }
//
//        String senhaCriptografada = passwordEncoder.encode(funcionarioCreateDTO.getSenha());
//
//        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();
//        funcionarioEntity.setNome(funcionarioCreateDTO.getFuncionario());
//        funcionarioEntity.setLogin(funcionarioCreateDTO.getLogin());
//        funcionarioEntity.setSenha(senhaCriptografada);
//        funcionarioEntity.setSituacao(funcionarioCreateDTO.getSituacao());
//        funcionarioEntity.setCpf(funcionarioCreateDTO.getCpf());
//        funcionarioEntity.setEspecialicazao(funcionarioCreateDTO.getEspecializacao());
//
//        Set<FuncionarioCargoPK> cargos = new HashSet<>();
//        for (FuncionarioCargoCreateDTO cargoDTO : funcionarioCreateDTO.getCargos()) {
//            CargoEntity cargoEntity = cargoService.buscarPorNomeCargo(cargoDTO.getNome());
//
//            FuncionarioCargoPK funcionarioCargo = new FuncionarioCargoPK();
//            funcionarioCargo.setCargoEntity(cargoEntity);
//            funcionarioCargo.setFuncionarioCargo(funcionarioEntity);
//
//            cargos.add(funcionarioCargo);
//        }
//
//        funcionarioEntity.setFuncionarioCargoPKS(cargos);
//        funcionarioRepository.save(funcionarioEntity);
//
//        return funcionarioConvertDTO(funcionarioEntity);
//    }

//    public FuncionarioDTO cadastrarFuncionario(FuncionarioCreateDTO funcionarioCreateDTO) {
//        // Cria um novo FuncionarioEntity com os valores do DTO
//        FuncionarioEntity funcionario = new FuncionarioEntity();
//        funcionario.setNome(funcionarioCreateDTO.getFuncionario());
//        funcionario.setCpf(funcionarioCreateDTO.getCpf());
//        funcionario.setEspecialicazao(funcionarioCreateDTO.getEspecializacao());
//        funcionario.setLogin(funcionarioCreateDTO.getLogin());
//        funcionario.setSenha(funcionarioCreateDTO.getSenha());
//        funcionario.setSituacao(funcionarioCreateDTO.getSituacao());
//
//        // Cria um HashSet vazio para os cargos
//        Set<CargoEntity> cargos = new HashSet<>();
//
//        // Para cada FuncionarioCargoCreateDTO no HashSet de cargos do FuncionarioCreateDTO
//        for (FuncionarioCargoCreateDTO cargoCreateDTO : funcionarioCreateDTO.getCargos()) {
//            // Busca o CargoEntity correspondente pelo nome
//            Optional<CargoEntity> optionalCargo = Optional.ofNullable(cargoService.buscarPorNomeCargo(cargoCreateDTO.getNome()));
//            if (optionalCargo.isPresent()) {
//                // Se encontrou o CargoEntity, adiciona à lista de cargos do FuncionarioEntity
//                cargos.add(optionalCargo.get());
//            } else {
//                // Se não encontrou o CargoEntity, lança uma exceção
//                throw new RuntimeException("Cargo " + cargoCreateDTO.getNome() + " não encontrado.");
//            }
//        }
//
//        // Adiciona a lista de cargos ao FuncionarioEntity
//        for (CargoEntity cargo : cargos) {
//            FuncionarioCargoPK funcionarioCargoPK = new FuncionarioCargoPK();
//            funcionarioCargoPK.setCargoEntity(cargo);
//            funcionarioCargoPK.setFuncionarioCargo(funcionario);
//            funcionario.addFuncionarioCargo(funcionarioCargoPK);
//        }
//
//        // Salva o FuncionarioEntity no banco de dados
//        FuncionarioEntity funcionarioSalvo = funcionarioRepository.save(funcionario);
//
//        // Retorna um FuncionarioDTO com os valores do FuncionarioEntity salvo
//        return funcionarioConvertDTO(funcionarioSalvo);
//    }


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
        return objectMapper.convertValue(buscarPorIdFuncionario(getIdLoggerdUser()), FuncionarioLogadoDTO.class);
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
