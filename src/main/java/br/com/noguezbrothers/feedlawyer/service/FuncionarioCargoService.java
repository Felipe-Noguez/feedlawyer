package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.funcionariocargtodto.FuncionarioCargoDTO;
import br.com.noguezbrothers.feedlawyer.entities.pk.FuncionarioCargoPK;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.FuncionarioCargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FuncionarioCargoService {
    private final FuncionarioCargoRepository funcionarioCargoRepository;

    public FuncionarioCargoPK buscarFuncionarioCargoPorNome(String nome) throws RegraDeNegocioException {
        return funcionarioCargoRepository.findByCargoEntity_NomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Não encontrado pela intermediária nome " + nome));
    }

    public FuncionarioCargoDTO converterFuncionarioCargoDTO (FuncionarioCargoPK funcionarioCargoPK) {
        FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
        funcionarioCargoDTO.setIdCargo(funcionarioCargoPK.getCargoEntity().getIdPerfil());
        funcionarioCargoDTO.setNome(funcionarioCargoPK.getCargoEntity().getNome());

        return funcionarioCargoDTO;
    }

}
