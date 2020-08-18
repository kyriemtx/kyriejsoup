package com.kyriemtx.jsoup.project.mapper;

import com.kyriemtx.jsoup.project.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int updateBatch(List<SysUser> list);

    int batchInsert(@Param("list") List<SysUser> list);

    SysUser selectByUserName(String userName);
}