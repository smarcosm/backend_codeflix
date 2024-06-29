package com.smarcosm.admin_catalogo.domain.exception;

public class InternalErrorException extends NoStackTraceException{

    protected InternalErrorException(final String aMessage, final Throwable t){
        super(aMessage, t);
    }
    public static InternalErrorException with(final String message, final Throwable t){
        return new InternalErrorException(message, t);
    }

}
