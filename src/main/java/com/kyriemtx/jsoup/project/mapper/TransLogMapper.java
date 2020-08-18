package com.kyriemtx.jsoup.project.mapper;

import com.kyriemtx.jsoup.project.entity.model.TransLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransLogMapper {
    int deleteByPrimaryKey(String reqSeqId);

    int insert(TransLog record);

    int insertSelective(TransLog record);

    TransLog selectByPrimaryKey(String reqSeqId);

    int updateByPrimaryKeySelective(TransLog record);

    int updateByPrimaryKey(TransLog record);

    int updateBatch(List<TransLog> list);

    int batchInsert(@Param("list") List<TransLog> list);
}