package com.kyriemtx.jsoup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kyriemtx.jsoup.project.mapper")
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class KyrieJsoupApplication {

    public static void main(String[] args) {
        SpringApplication.run(KyrieJsoupApplication.class, args);
    }

}
