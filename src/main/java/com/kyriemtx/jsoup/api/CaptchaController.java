package com.kyriemtx.jsoup.api;

import com.kyriemtx.jsoup.common.AjaxResult;
import com.kyriemtx.jsoup.common.Constants;
import com.kyriemtx.jsoup.util.Base64;
import com.kyriemtx.jsoup.util.VerifyCodeUtils;
import com.kyriemtx.jsoup.util.redis.RedisCache;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CaptchaController
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/2/27 15:56
 **/
@RestController
public class CaptchaController {

    @Autowired
    private RedisCache redisCache;

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        GifCaptcha gifCaptcha = new GifCaptcha(130,48,4);
        CaptchaUtil.out(gifCaptcha, request, response);
        String verCode = gifCaptcha.text().toLowerCase();
        request.getSession().setAttribute("CAPTCHA",verCode);
    }

    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 验证码 redis key
        String verifyKey = Constants.CAPTCHA_CODE_KEY;
        redisCache.setCacheObject(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            String code = Base64.encode(stream.toByteArray());
            AjaxResult ajax = AjaxResult.success("操作成功");
            return ajax;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        finally
        {
            stream.close();
        }
    }


}
