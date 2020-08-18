package com.kyriemtx.jsoup.api;

import com.kyriemtx.jsoup.common.AjaxResult;
import com.kyriemtx.jsoup.common.RespCodeEnum;
import com.kyriemtx.jsoup.project.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/18 14:33
 **/
@RestController
@Slf4j
public class TestController {

    @Autowired
    CommonService commonService;

    @GetMapping("/parse1")
    public AjaxResult testParese1(String fileName){
        AjaxResult ajaxResult = new AjaxResult();
        commonService.insertParseTransLogs(fileName);
        ajaxResult.setCode(RespCodeEnum.SUCCESS.getCode());
        ajaxResult.setDesc(RespCodeEnum.SUCCESS.getDesc());
        return ajaxResult;
    }



}
