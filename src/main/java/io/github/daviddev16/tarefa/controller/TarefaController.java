package io.github.daviddev16.tarefa.controller;

import io.github.daviddev16.departamento.projection.DepartamentoSumarioProjection;
import io.github.daviddev16.pessoa.service.PessoaService;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.converter.TarefaDTOConverter;
import io.github.daviddev16.tarefa.dto.request.CriarTarefaRequestDTO;
import io.github.daviddev16.tarefa.dto.response.TarefaResponseDTO;
import io.github.daviddev16.tarefa.service.TarefaAlocacaoService;
import io.github.daviddev16.tarefa.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;
    private final PessoaService pessoaService;
    private final TarefaAlocacaoService tarefaAlocacaoService;

    public TarefaController(TarefaService tarefaService,
                            PessoaService pessoaService,
                            TarefaAlocacaoService tarefaAlocacaoService) {
        this.tarefaService = tarefaService;
        this.pessoaService = pessoaService;
        this.tarefaAlocacaoService = tarefaAlocacaoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<TarefaResponseDTO> obterListaTarefas() {
        return tarefaService.obterTarefas()
                .stream()
                .map(TarefaDTOConverter::converterParaResponseDTO)
                .toList();
    }

    @GetMapping("/pendentes")
    @ResponseStatus(HttpStatus.FOUND)
    public List<TarefaResponseDTO> obterListaTarefasPendentes(
            @RequestParam(required = false) Integer quantidadeTarefas) {
        return tarefaService.obterTarefasPendentes(quantidadeTarefas)
                .stream()
                .map(TarefaDTOConverter::converterParaResponseDTO)
                .toList();
    }

    @GetMapping("/departamentos")
    @ResponseStatus(HttpStatus.FOUND)
    public List<DepartamentoSumarioProjection> obterListaSumarioDepartamentos() {
        return tarefaService.obterSumarioDepartamentos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TarefaResponseDTO criarTarefa(@RequestBody CriarTarefaRequestDTO criarTarefaRequestDTO) {
        Tarefa tarefa = tarefaService.criarTarefa(criarTarefaRequestDTO);
        return TarefaDTOConverter.converterParaResponseDTO(tarefa);
    }

    @PutMapping("/{tarefaId}/alocar/{pessoaId}")
    @ResponseStatus(HttpStatus.OK)
    public TarefaResponseDTO alocarPessoaParaTarefa(@PathVariable("tarefaId") Long tarefaId,
                                                    @PathVariable("pessoaId") Long pessoaId) {
        Tarefa tarefa = pessoaService.alocarPessoaEmTarefaPorId(pessoaId, tarefaId);
        return TarefaDTOConverter.converterParaResponseDTO(tarefa);
    }

    @PutMapping("/alocar/{tarefaId}")
    @ResponseStatus(HttpStatus.OK)
    public TarefaResponseDTO encontrarEAlocarPessoaParaTarefa(@PathVariable("tarefaId") Long tarefaId) {
        Tarefa tarefa = tarefaAlocacaoService
                .encontrarEAlocarPessoaDisponivelEmTarefaPorId(tarefaId);
        return TarefaDTOConverter.converterParaResponseDTO(tarefa);
    }

    @PutMapping("/finalizar/{tarefaId}")
    @ResponseStatus(HttpStatus.OK)
    public TarefaResponseDTO finalizarTarefa(@PathVariable("tarefaId") Long tarefaId) {
        Tarefa tarefaFinalizada = tarefaService.encerrarTarefaPorId(tarefaId);
        return TarefaDTOConverter.converterParaResponseDTO(tarefaFinalizada);
    }

}
