package com.smarcosm.admin_catalogo.domain.exception;

public class NoStackTraceException extends RuntimeException{
    public NoStackTraceException(final String message) {
        this(message, null);
    }

    public NoStackTraceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
