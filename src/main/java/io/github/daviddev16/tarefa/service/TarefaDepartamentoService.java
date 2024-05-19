package io.github.daviddev16.tarefa.service;

import io.github.daviddev16.departamento.Departamento;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.TarefaDepartamento;

public interface TarefaDepartamentoService {

    void vincularDepartamentoEmTarefa(Long tarefaId, Long departamentoId);

    default void vincularDepartamentoEmTarefa(Tarefa tarefa, Departamento departamento) {
        vincularDepartamentoEmTarefa(tarefa.getId(), departamento.getId());
    }

    void desvincularDepartamentoDeTarefa(Long tarefaId, Long departamentoId);

    default void desvincularDepartamentoDeTarefa(Tarefa tarefa, Departamento departamento) {
        desvincularDepartamentoDeTarefa(tarefa.getId(), departamento.getId());
    }

    TarefaDepartamento obterTarefaDepartamentoPorId(Long tarefaId, Long departamentoId);

}
