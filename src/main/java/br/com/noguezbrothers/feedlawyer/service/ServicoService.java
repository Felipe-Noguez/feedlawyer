package br.com.noguezbrothers.feedlawyer.service;

import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.entities.FuncionarioEntity;
import br.com.noguezbrothers.feedlawyer.entities.ServicoEntity;
import br.com.noguezbrothers.feedlawyer.entities.pk.ServicoFuncionarioPK;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final FuncionarioService funcionarioService;

    public ServicoDTO cadastrarServico(ServicoCreateDTO servicoCreateDTO) throws RegraDeNegocioException {
        ServicoEntity servicoEntity = new ServicoEntity();
        ServicoFuncionarioPK servicoFuncionarioPK = new ServicoFuncionarioPK();
//        FuncionarioLogadoDTO funcionarioLogado = funcionarioService.getLoggedUser();
        FuncionarioEntity funcionarioEntity = funcionarioService.buscarPorIdFuncionario(funcionarioService.getLoggedUser().getIdFuncionario());

        servicoEntity.setDescricao(servicoCreateDTO.getDescricao());

        servicoFuncionarioPK.setServicoFuncionario(funcionarioEntity);
        servicoFuncionarioPK.setServicoEntity(servicoEntity);

        Set<ServicoFuncionarioPK> funcionarios = new HashSet<>();
        funcionarios.add(servicoFuncionarioPK);
        servicoEntity.setServicoFuncionarioPKS(funcionarios);

        servicoRepository.save(servicoEntity);

        ServicoDTO servicoDTO = new ServicoDTO(servicoEntity);
        return new ServicoDTO(servicoEntity);
    }

    public ServicoEntity getServicoEntityById(Integer idServico) throws RegraDeNegocioException {
        return servicoRepository.findById(idServico)
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado"));
    }
}
