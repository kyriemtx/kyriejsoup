package com.kyriemtx.jsoup.common;

import lombok.Data;

/**
 * @ClassName AjaxResult
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/13 11:07
 **/
@Data
public class AjaxResult {

    /** 响应码 **/
    private String code;

    /** 返回描述 **/
    private String desc;

    /** 相应数据 **/
    private Object data;

    public AjaxResult(){}




    /**
     * 初始化一个新创建的 AjaxResult 对象
     * @param code 状态码
     * @param desc 返回描述
     */
    public AjaxResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    /**
     * 初始化一个新创建的 AjaxResult 对象
     * @param code  状态码
     * @param desc  返回描述
     * @param data  数据对象
     */
    public AjaxResult(String code, String desc, Object data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    /**
     * 返回成功数据
     * @param data
     */
    public AjaxResult(Object data){
        this.code = RespCodeEnum.SUCCESS.getCode();
        this.desc = RespCodeEnum.SUCCESS.getDesc();
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(RespCodeEnum.SUCCESS.getCode(), msg, data);
    }


    /**
     * 返回错误消息
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(RespCodeEnum.FAILURE.getCode(), msg, data);
    }

}
