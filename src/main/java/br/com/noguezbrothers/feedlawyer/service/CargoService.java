package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.entities.CargoEntity;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoEntity buscarPorIdCargo(Integer idCargo) throws RegraDeNegocioException {
        return cargoRepository.findById(idCargo)
                .orElseThrow(()-> new RegraDeNegocioException("Cargo não encontrado"));
    }

    public CargoEntity buscarPorNomeCargo (String nome) {
        return cargoRepository.findAllByNomeContainingIgnoreCase(nome);
    }
}
