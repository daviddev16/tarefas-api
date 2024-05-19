package io.github.daviddev16.core;

import static java.lang.String.format;

public class ServicoException extends RuntimeException {

    public ServicoException() {}

    public ServicoException(String mensagemFormatada, Object... vargs) {
        super(format(mensagemFormatada, vargs));
    }

    public ServicoException(String message) {
        super(message);
    }

}
