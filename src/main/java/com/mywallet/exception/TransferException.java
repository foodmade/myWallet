package com.mywallet.exception;

/**
 * 转账异常
 *
 * @author linapex
 */
public class TransferException extends BaseException {
    private static final long serialVersionUID = 1L;

    public TransferException(Throwable ex) {
        super(ex);
    }

    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable ex) {
        super(message, ex);
    }

    @Override
    public int getErrorCode() {
        return ErrorCodeCons.TransferException;
    }

}
