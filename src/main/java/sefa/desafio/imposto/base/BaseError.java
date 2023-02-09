package sefa.desafio.imposto.base;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BaseError {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime timestamp = LocalDateTime.now();
    public String error;
    public String message;

    public BaseError(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
