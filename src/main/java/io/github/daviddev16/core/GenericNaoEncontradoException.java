package io.github.daviddev16.core;

public class GenericNaoEncontradoException extends ServicoException {

    public GenericNaoEncontradoException(String mensagemFormatada, Object... vargs) {
        super(mensagemFormatada, vargs);
    }

    public GenericNaoEncontradoException(String message) {
        super(message);
    }
}
