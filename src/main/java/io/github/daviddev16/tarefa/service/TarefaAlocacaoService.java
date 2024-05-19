package io.github.daviddev16.tarefa.service;

import io.github.daviddev16.tarefa.Tarefa;

public interface TarefaAlocacaoService {

    Tarefa encontrarEAlocarPessoaDisponivelEmTarefaPorId(Long id);

}
