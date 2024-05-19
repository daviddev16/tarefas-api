package io.github.daviddev16.handler;

import io.github.daviddev16.core.ApiMensagem;
import io.github.daviddev16.core.GenericNaoEncontradoException;
import io.github.daviddev16.core.ServicoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServicoException.class)
    ApiMensagem handleServicoException(ServicoException servicoException) {
        return ApiMensagem.erroMensagem(servicoException);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GenericNaoEncontradoException.class)
    ApiMensagem handleGenericNaoEncontradoException(GenericNaoEncontradoException naoEncontradoException) {
        return ApiMensagem.erroMensagem(naoEncontradoException);
    }

    /* para quando for usado uma função anotada como @Transactional. */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    ApiMensagem handleDataIntegrityViolationException(DataIntegrityViolationException violationException) {
        return ApiMensagem.mensagem("Nome já em uso.");
    }

}
