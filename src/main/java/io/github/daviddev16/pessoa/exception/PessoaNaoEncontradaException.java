package io.github.daviddev16.pessoa.exception;

import io.github.daviddev16.core.GenericNaoEncontradoException;

public class PessoaNaoEncontradaException extends GenericNaoEncontradoException {

    public PessoaNaoEncontradaException(Long id) {
        super("Não foi possível localizar uma pessoa com id \"%d\".", id);
    }

    public PessoaNaoEncontradaException(String nome) {
        super("Não foi possível localizar uma pessoa com o nome \"%s\".", nome);
    }
}
