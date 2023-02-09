package sefa.desafio.imposto.domain.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sefa.desafio.imposto.base.BaseError;
import sefa.desafio.imposto.base.BaseException;

import java.util.Locale;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseError>  handleNegocioExeption(BaseException ex) {
        var message = ex.getMessage();
        var description = messageSource.getMessage(message, null, Locale.ROOT);
        var baseError = new BaseError(message, description);
        return new ResponseEntity<>(baseError, HttpStatus.valueOf(500));
    }
}
