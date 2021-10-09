package com.exception;

/**
 * @author zxy
 * @date 2021/7/2 9:39
 * @description
 */
public class NLException extends Exception {
    private int resType;
    private int resCode;

    public NLException() {
    }

    public NLException(String message, Throwable cause) {
        super(message, cause);
    }

    public NLException(String message) {
        super(message);
    }

    public NLException(Throwable cause) {
        super(cause);
    }

    public NLException(int code) {
        this.resCode = code;
    }

    public NLException(int code, String message, Throwable cause) {
        super(message, cause);
        this.resCode = code;
    }

    public NLException(int resType, int resCode, String message, Throwable cause) {
        super(message, cause);
        this.resCode = resCode;
        this.resType = resType;
    }

    public NLException(int code, String message) {
        super(message);
        this.resCode = code;
    }

    public NLException(int resType, int resCode, String message) {
        super(message);
        this.resCode = resCode;
        this.resType = resType;
    }

    public NLException(int code, Throwable cause) {
        super(cause);
        this.resCode = code;
    }

    public void setResCode(int code) {
        this.resCode = code;
    }

    public int getResCode() {
        return this.resCode;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }

    public int getResType() {
        return this.resType;
    }
}
