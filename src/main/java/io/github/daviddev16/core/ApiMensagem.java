package io.github.daviddev16.core;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class ApiMensagem {

    private List<String> mensagens;

    public static ApiMensagem mensagem(String mensagem, Object... vargs) {
        return new ApiMensagem(format(mensagem, vargs));
    }

    public static ApiMensagem erroMensagem(Exception exception) {
        return new ApiMensagem(exception.getMessage());
    }

    public static ApiMensagem mensagem(List<String> mensagens) {
        return new ApiMensagem(mensagens);
    }

    private ApiMensagem(List<String> mensagens) {
        this.mensagens = mensagens;
    }

    private ApiMensagem(String mensagem) {
        this(Arrays.asList(mensagem));
    }

    public List<String> getMensagens() {
        return mensagens;
    }
}
