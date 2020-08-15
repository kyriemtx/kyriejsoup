package com.kyriemtx.jsoup.project.entity;

import lombok.Data;

/**
 * @ClassName GoodInfo
 * @Description   商品信息
 * @Author tengxiao.ma
 * @Date 2020/8/15 13:55
 **/
@Data
public class GoodInfo {

    /** 商品ID **/
    private String id;

    /** 商品名称 **/
    private String name;

    /** 商品价格 **/
    private String price;

    /** 商品地址 **/
    private String goodsUrl;

    /** 图片地址 **/
    private String imgUrl;

    /** 商品店铺 **/
    private String goodsShop;

    /** 月销售量 **/
    private String monthNumber;

    /** 评论数量 **/
    private String commit;


}
