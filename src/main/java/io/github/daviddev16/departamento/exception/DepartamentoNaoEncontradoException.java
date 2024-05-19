package io.github.daviddev16.departamento.exception;

import io.github.daviddev16.core.GenericNaoEncontradoException;

public class DepartamentoNaoEncontradoException extends GenericNaoEncontradoException {

    public DepartamentoNaoEncontradoException(Long id) {
        super("Não foi possível localizar um departamento com id \"%d\".", id);
    }

    public DepartamentoNaoEncontradoException(String nome) {
        super("Não foi possível localizar um departamento com o nome \"%s\".", nome);
    }
}
