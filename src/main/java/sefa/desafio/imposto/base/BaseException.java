package sefa.desafio.imposto.base;

public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message, null, true, false);
    }
}
