package io.github.daviddev16.tarefa.impl;

import io.github.daviddev16.core.GenericNaoEncontradoException;
import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.departamento.service.DepartamentoService;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.TarefaDepartamento;
import io.github.daviddev16.tarefa.repository.TarefaDepartamentoRepository;
import io.github.daviddev16.tarefa.service.TarefaDepartamentoService;
import io.github.daviddev16.tarefa.service.TarefaService;
import org.springframework.stereotype.Service;

public @Service class TarefaDepartamentoServiceImpl implements TarefaDepartamentoService {

    private final TarefaDepartamentoRepository tarefaDepartamentoRepository;

    private final DepartamentoService departamentoService;
    private final TarefaService tarefaService;

    public TarefaDepartamentoServiceImpl(TarefaDepartamentoRepository tarefaDepartamentoRepository,
                                         DepartamentoService departamentoService,
                                         TarefaService tarefaService) {
        this.tarefaDepartamentoRepository = tarefaDepartamentoRepository;
        this.departamentoService = departamentoService;
        this.tarefaService = tarefaService;
    }


    @Override
    public void vincularDepartamentoEmTarefa(Long tarefaId, Long departamentoId) {
        Tarefa tarefa = tarefaService.obterTarefaPorId(tarefaId);
        Departamento departamento = departamentoService.obterDepartamentoPorId(departamentoId);

        TarefaDepartamento tarefaDepartamento = new TarefaDepartamento(tarefa, departamento);
        tarefaDepartamentoRepository.save(tarefaDepartamento);

        tarefa.getTarefaDepartamentos().add(tarefaDepartamento);
    }

    @Override
    public void desvincularDepartamentoDeTarefa(Long tarefaId, Long departamentoId) {
        Tarefa tarefa = tarefaService.obterTarefaPorId(tarefaId);

        TarefaDepartamento tarefaDepartamento = obterTarefaDepartamentoPorId(tarefaId, departamentoId);
        tarefaDepartamentoRepository.delete(tarefaDepartamento);

        tarefa.getTarefaDepartamentos().remove(tarefaDepartamento);
    }

    @Override
    public TarefaDepartamento obterTarefaDepartamentoPorId(Long tarefaId, Long departamentoId) {
        return tarefaDepartamentoRepository
                .findByTarefaAndDepartamentoId(tarefaId, departamentoId)
                .orElseThrow(() -> new GenericNaoEncontradoException("A tarefa \"%d\" não possui" +
                        " vínculo com o departamento \"%d\".", tarefaId, departamentoId));
    }
}
