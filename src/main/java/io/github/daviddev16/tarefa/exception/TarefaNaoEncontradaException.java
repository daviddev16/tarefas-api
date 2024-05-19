package io.github.daviddev16.tarefa.exception;

import io.github.daviddev16.core.GenericNaoEncontradoException;

public class TarefaNaoEncontradaException extends GenericNaoEncontradoException {

    public TarefaNaoEncontradaException(Long id) {
        super("Não foi possível localizar uma tarefa com id \"%d\".", id);
    }

    public TarefaNaoEncontradaException(String titulo) {
        super("Não foi possível localizar uma tarefa com o título \"%s\".", titulo);
    }
}
