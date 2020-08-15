package com.kyriemtx.jsoup;

import com.kyriemtx.jsoup.util.redis.RedisUtils;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName MainTest
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/2/28 11:22
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KyrieJsoupApplication.class)
public class MainTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test(){
        String inputCode = "xadm";
        String key = "0:0:0:0:0:0:0:1_aaec21aa3fd3414297aa88f6f84e116e";
        String redisCode = (String)redisUtils.get(key);
        if(StringUtils.isNotBlank(redisCode)){
            if(redisCode.equals(inputCode.trim().toLowerCase())){
                System.out.println("验证码正确");
            }
            else {
                System.err.println("验证码错误");
            }
        }
        else {
            System.err.println("未获取到验证码");
        }
    }
}
