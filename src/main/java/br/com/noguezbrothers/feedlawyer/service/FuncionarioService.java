package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.funcionario.FuncionarioDTO;
import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import br.com.noguezbrothers.feedlawyer.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

        CargoEntity cargo = new CargoEntity();
        funcionarioEntity.setCargos(Set.of(cargo));

        return funcionarioConvertDTO(funcionarioRepository.save(funcionarioEntity));
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
                funcionarioEntity.getSenha(),
                funcionarioEntity.getTipoPerfil());
    }
}
