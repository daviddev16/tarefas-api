package io.github.daviddev16.tarefa.impl;

import io.github.daviddev16.pessoa.Pessoa;
import io.github.daviddev16.pessoa.service.PessoaService;
import io.github.daviddev16.tarefa.Tarefa;
import io.github.daviddev16.tarefa.service.TarefaAlocacaoService;
import io.github.daviddev16.tarefa.service.TarefaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public @Service class TarefaAlocaoServiceImpl implements TarefaAlocacaoService {

    private final TarefaService tarefaService;
    private final PessoaService pessoaService;

    public TarefaAlocaoServiceImpl(TarefaService tarefaService, PessoaService pessoaService) {
        this.tarefaService = tarefaService;
        this.pessoaService = pessoaService;
    }

    @Override
    @Transactional
    public Tarefa encontrarEAlocarPessoaDisponivelEmTarefaPorId(Long tarefaId) {
        Pessoa pessoaDisponivel = pessoaService.obterPessoaDisponivelParaTarefaPorId(tarefaId);
        return pessoaService.alocarPessoaEmTarefaPorId(pessoaDisponivel.getId(), tarefaId);
    }
}
