package com.kyriemtx.jsoup.api;

import com.kyriemtx.jsoup.common.AjaxResult;
import com.kyriemtx.jsoup.common.PCommon;
import com.kyriemtx.jsoup.common.RespCodeEnum;
import com.kyriemtx.jsoup.exception.BizException;
import com.kyriemtx.jsoup.project.service.JDService;
import com.kyriemtx.jsoup.project.service.TBService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ProductController
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/15 12:12
 **/
@RestController
@Slf4j
@RequestMapping("/jsoup")
public class ProductController {

    @Autowired
    private TBService tbService;
    @Autowired
    private JDService jdService;


    @GetMapping("/jd")
    public AjaxResult jdProductQuery(String input){
        log.info("【根据用户输入搜索京东商城商品信息】，请求参数：{}", input);
        AjaxResult ajaxResult = new AjaxResult();
        String url;
        try {
            if(StringUtils.isBlank(input)){
                url = PCommon.JDCOM;
            }else {
                url = PCommon.JDURL + input + PCommon.UFT8;
            }
            ajaxResult = jdService.downJDProduct(url);
            ajaxResult.setCode(RespCodeEnum.SUCCESS.getCode());
            ajaxResult.setDesc(RespCodeEnum.SUCCESS.getDesc());
        }catch (BizException e){
            log.error("【根据用户输入搜索京东商城商品信息】业务异常，异常信息：{}",e);
            ajaxResult.setCode(e.getCode());
            ajaxResult.setDesc(e.getMessage());
        }catch (Exception e){
            log.error("【根据用户输入搜索京东商城商品信息】异常，异常信息：{}",e);
            ajaxResult.setCode(RespCodeEnum.FAILURE.getCode());
            ajaxResult.setDesc(RespCodeEnum.FAILURE.getDesc());
        }
        log.info("【根据用户输入搜索京东商城商品信息】响应结果：{}",ajaxResult);
        return ajaxResult;
    }


    @GetMapping("/tb")
    public AjaxResult tbProductQuery(String input){
        log.info("【根据用户输入搜索天猫商城商品信息】，请求参数：{}", input);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String url = PCommon.TBURL + input;
            ajaxResult = tbService.downTBProduct(url);
            ajaxResult.setCode(RespCodeEnum.SUCCESS.getCode());
            ajaxResult.setDesc(RespCodeEnum.SUCCESS.getDesc());
        }catch (BizException e){
            log.error("【根据用户输入搜索天猫商城商品信息】业务异常，异常信息：{}",e);
            ajaxResult.setCode(e.getCode());
            ajaxResult.setDesc(e.getMessage());
        }catch (Exception e){
            log.error("【根据用户输入搜索天猫商城商品信息】异常，异常信息：{}",e);
            ajaxResult.setCode(RespCodeEnum.FAILURE.getCode());
            ajaxResult.setDesc(RespCodeEnum.FAILURE.getDesc());
        }
        log.info("【根据用户输入搜索天猫商城商品信息】响应结果：{}",ajaxResult);
        return ajaxResult;
    }


}
