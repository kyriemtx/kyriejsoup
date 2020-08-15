package com.kyriemtx.jsoup.common;

/**
 * @ClassName RespCodeEnum
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/13 11:08
 **/
public enum RespCodeEnum {

    SUCCESS("000000","成功"),
    FAILURE("999999","失败"),

    CAPTCHA_NOT_EXIST("100001","验证码不存在或已过期"),
    CAPTCHA_ERROR("100002","验证码有误"),
    LOGIN_ERROR("100003","账号或密码有误"),
    ;


    private String code;
    private String desc;

    RespCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
