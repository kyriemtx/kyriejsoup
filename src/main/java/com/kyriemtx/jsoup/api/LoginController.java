package com.kyriemtx.jsoup.api;


import com.kyriemtx.jsoup.common.AjaxResult;
import com.kyriemtx.jsoup.common.RespCodeEnum;
import com.kyriemtx.jsoup.exception.BizException;
import com.kyriemtx.jsoup.project.entity.SysUser;
import com.kyriemtx.jsoup.project.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/2/27 16:37
 **/
@RestController
@Slf4j
public class LoginController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/login")
    public AjaxResult login(SysUser sysUser){
        log.info("【用户登录】请求参数 SysUser:{}",sysUser);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            SysUser user = sysUserService.selectByUserName(sysUser.getUserName());
            if(ObjectUtils.isNotEmpty(user)){
                ajaxResult.setCode(RespCodeEnum.SUCCESS.getCode());
                ajaxResult.setDesc(RespCodeEnum.SUCCESS.getDesc());
                ajaxResult.setData(user);
            }
        }catch (BizException e){
            log.error("【用户登录】业务异常，异常信息：{}",e);
            ajaxResult.setCode(e.getCode());
            ajaxResult.setDesc(e.getMessage());
        }catch (Exception e){
            log.error("【用户登录】异常，异常信息：{}",e);
            ajaxResult.setCode(RespCodeEnum.FAILURE.getCode());
            ajaxResult.setDesc(RespCodeEnum.FAILURE.getDesc());
        }
        log.info("【用户登录】响应结果：{}",ajaxResult);
        return ajaxResult;
    }

}
