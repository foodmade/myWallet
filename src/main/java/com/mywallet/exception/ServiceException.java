package com.mywallet.exception;

/**
 * ServiceException
 *
 * @author linapex
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServiceException(Throwable ex) {
        super(ex);
    }

    public ServiceException() {
        this("服务功能异常");
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable ex) {
        super(message, ex);
    }

    public int getErrorCode() {
        return ErrorCodeCons.GENERAL_ERRORCODE;
    }

}
