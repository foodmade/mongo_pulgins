package com.generate.common.exception;

public class ParamsInvalidException extends RuntimeException{

    private Integer err_code = 500;
    private String  err_info;

    public ParamsInvalidException(String message){
        super(message);
        this.err_info = message;
    }

    public ParamsInvalidException(){}

    public ParamsInvalidException(Integer code, String message){
        super(message);
        this.err_code = code;
        this.err_info = message;
    }

    public Integer getErr_code() {
        return err_code;
    }

    public void setErr_code(Integer err_code) {
        this.err_code = err_code;
    }

    public String getErr_info() {
        return err_info;
    }

    public void setErr_info(String err_info) {
        this.err_info = err_info;
    }
}
