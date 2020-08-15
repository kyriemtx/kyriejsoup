package com.kyriemtx.jsoup.exception;

import com.kyriemtx.jsoup.common.RespCodeEnum;

/**
 * @ClassName BizException
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/13 14:14
 **/
public class BizException extends RuntimeException {
    private String message;
    private String code;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(String code, String message) {
        this.message = message;
        this.code = code;
    }
    public BizException(RespCodeEnum respCodeEnum) {
        this.message = respCodeEnum.getDesc();
        this.code = respCodeEnum.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BizException{");
        sb.append("message='").append(message).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
