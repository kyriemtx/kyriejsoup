package com.kyriemtx.jsoup.project.service;

import com.kyriemtx.jsoup.project.entity.SysUser;
import com.kyriemtx.jsoup.project.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysUserService
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/16 12:24
 **/
@Service
@Slf4j
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;


    public SysUser selectByUserName(String userName){
        log.info("【根据用户名查询用户信息】请求参数 userName：{}",userName);
        SysUser sysUser = sysUserMapper.selectByUserName(userName);
        return sysUser;
    }
}
