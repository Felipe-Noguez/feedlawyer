package br.com.noguezbrothers.feedlawyer.controller;

import br.com.noguezbrothers.feedlawyer.dto.paginacaodto.PageDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoCreateDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoDTO;
import br.com.noguezbrothers.feedlawyer.dto.servico.ServicoPageDTO;
import br.com.noguezbrothers.feedlawyer.enums.Situacao;
import br.com.noguezbrothers.feedlawyer.exceptions.RegraDeNegocioException;
import br.com.noguezbrothers.feedlawyer.service.ServicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @PostMapping
    public ResponseEntity<ServicoDTO> cadastrarServico(@RequestBody ServicoCreateDTO servicoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(servicoService.cadastrarServico(servicoCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<PageDTO<ServicoPageDTO>> listarAvaliacoes(@RequestParam(required = false) String nomeCliente,
                                                                    @RequestParam(required = false) Integer idServico,
                                                                    @RequestParam(required = false) String descricao,
                                                                    @RequestParam(required = false) String nomeAdvogado,
                                                                    @RequestParam(required = false) Double notaAvaliacao,
                                                                    @RequestParam(required = false) Situacao situacao,
                                                                    @RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size) throws RegraDeNegocioException {
        return new ResponseEntity<>(servicoService.listarServicos(nomeCliente, idServico, descricao, nomeAdvogado, notaAvaliacao, situacao, page, size), HttpStatus.OK);
    }

    @PutMapping("/{idServico}/desativar")
    public ResponseEntity<Void> desativarServico(@PathVariable("idServico") Integer idServico) throws RegraDeNegocioException {
        servicoService.desativarServico(idServico);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idServico}/remover")
    public ResponseEntity<Void> removerServico(@PathVariable("idServico") Integer idServico) throws RegraDeNegocioException {
        servicoService.removerServico(idServico);

        return ResponseEntity.noContent().build();
    }
}
