package com.lorgen.calculator.exception;

public class EvalutationException extends Exception {

    public EvalutationException() {
    }

    public EvalutationException(String message) {
        super(message);
    }

    public EvalutationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvalutationException(Throwable cause) {
        super(cause);
    }

    public EvalutationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
