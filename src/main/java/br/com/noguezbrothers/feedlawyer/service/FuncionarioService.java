package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.funcionario.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioDTO;
import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    public FuncionarioDTO cadastrarFuncionario(FuncionarioCreateDTO funcionarioCreateDTO) {

        String senhaCriptografada = passwordEncoder.encode(funcionarioCreateDTO.getSenha());
        FuncionarioEntity funcionarioEntity = funcionarioConverterEntity(funcionarioCreateDTO);
        //findbyid em cargo
        funcionarioEntity.setNome(funcionarioCreateDTO.getNomeFuncionario());
        funcionarioEntity.setCpf(funcionarioCreateDTO.getCpf());
        funcionarioEntity.setEspecialicazao(funcionarioCreateDTO.getEspecializacao());
        funcionarioEntity.setLogin(funcionarioCreateDTO.getLogin());
        funcionarioEntity.setSenha(senhaCriptografada);

        CargoEntity cargo = new CargoEntity(null, "ROLE_ADMINISTRADOR", new HashSet<>());
//        funcionarioEntity.setCargos(Set.of(cargo));
//        funcionarioEntity.setCargos(Set.of(cargo, cargo));

        return funcionarioConvertDTO(funcionarioRepository.save(funcionarioEntity));
    }

    public PageDTO<FuncionarioDTO> listarFuncionarios(String nome, String cpf, String especializacao, Integer tipoPerfil, Integer idFuncionario, Integer page, Integer size) throws RegraDeNegocioException {
        if (page < 0 || size < 0) {
            throw new RegraDeNegocioException("Tamanho da página ou de elementos não podem ser menor que 0.");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FuncionarioEntity> funcionarioEntities = funcionarioRepository.listarFuncionarios(nome, cpf, especializacao, tipoPerfil, idFuncionario, pageRequest);

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
        funcionarioEntity.setNome(funcionario.getNomeFuncionario());
        funcionarioEntity.setCpf(funcionario.getCpf());
        funcionarioEntity.setEspecialicazao(funcionario.getEspecializacao());
        funcionarioEntity.setLogin(funcionario.getLogin());
        funcionarioEntity.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        funcionarioEntity.setTipoPerfil(funcionario.getTipoPerfil());

        funcionarioRepository.save(funcionarioEntity);

        return funcionarioConvertDTO(funcionarioEntity);
    }

    public void removerFuncionario(String cpf ) throws RegraDeNegocioException {
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

    private FuncionarioEntity funcionarioConverterEntity(FuncionarioCreateDTO funcionarioCreateDTO) {
        return new FuncionarioEntity(null,
                funcionarioCreateDTO.getNomeFuncionario(),
                funcionarioCreateDTO.getCpf(),
                funcionarioCreateDTO.getEspecializacao(),
                funcionarioCreateDTO.getLogin(),
                funcionarioCreateDTO.getSenha(),
                funcionarioCreateDTO.getTipoPerfil(),
                new HashSet<>(),
                new HashSet<>());
    }

    private FuncionarioDTO funcionarioConvertDTO(FuncionarioEntity funcionarioEntity) {

        return new FuncionarioDTO(funcionarioEntity.getIdFuncionario(),
                funcionarioEntity.getNome(),
                funcionarioEntity.getEspecialicazao(),
                funcionarioEntity.getLogin(),
                funcionarioEntity.getCpf(),
                funcionarioEntity.getTipoPerfil());
    }
}
