package com.kyriemtx.jsoup.project.service;

import com.kyriemtx.jsoup.project.entity.model.TransLog;
import com.kyriemtx.jsoup.project.mapper.TransLogMapper;
import com.kyriemtx.jsoup.util.file.FileCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ParseFileService
 * @Description  公用服务
 * @Author tengxiao.ma
 * @Date 2020/8/18 14:26
 **/
@Slf4j
@Service
public class CommonService {

    @Autowired
    private TransLogMapper transLogMapper;


    public void insertParseTransLogs(String fileName){
        List<TransLog> transLogs = FileCommonUtil.parseFile(fileName);
        if(transLogs.size() >0){
            log.info("【批量插入解析出来的交易日志】");
            transLogMapper.batchInsert(transLogs);
            log.info("【解析并落库成功】");
        }
    }

}
