package com.generate.common.exception;

public class CommonException extends RuntimeException {

    private Integer err_code = 503;
    private String  err_info;

    public CommonException(String message){
        super(message);
        this.err_info = message;
    }

    public CommonException(){}

    public CommonException(Integer code, String message){
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
