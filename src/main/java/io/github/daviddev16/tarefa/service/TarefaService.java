package io.github.daviddev16.tarefa.service;

import io.github.daviddev16.departamento.projection.DepartamentoSumarioProjection;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.dto.request.CriarTarefaRequestDTO;

import java.util.List;

public interface TarefaService {

    Tarefa obterTarefaPorId(Long id);

    Tarefa encerrarTarefaPorId(Long id);

    List<Tarefa> obterTarefas();

    List<Tarefa> obterTarefasPendentes(Integer limite);

    Tarefa criarTarefa(CriarTarefaRequestDTO criarTarefaRequestDTO);

    List<DepartamentoSumarioProjection> obterSumarioDepartamentos();

    void excluirTarefa(Tarefa tarefa);

    default void excluirTarefaPorId(Long id) {
        Tarefa tarefa = obterTarefaPorId(id);
        excluirTarefa(tarefa);
    }

}
