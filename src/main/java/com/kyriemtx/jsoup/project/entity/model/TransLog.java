package com.kyriemtx.jsoup.project.entity.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName TransLog
 * @Description  交易日志  模拟解析文件
 * @Author tengxiao.ma
 * @Date 2020/8/18 13:22
 **/

@Data
public class TransLog implements Serializable {

    static final String TRIM = "\\s*(\\S+)\\s*";


    /**
     * 请求流水号
     */
    @CsvBindByPosition(position = 0,capture = TRIM)
    private String reqSeqId;

    /**
     * 交易日期
     */
    @CsvBindByPosition(position = 1,capture = TRIM)
    private String transDate;

    /**
     * 交易金额
     */
    @CsvBindByPosition(position = 2,capture = TRIM)
    private BigDecimal amount;

    /**
     * 通道对账状态
     */
    @CsvBindByPosition(position = 3,capture = TRIM)
    private String channelStat;

    /**
     * 通道对账日期
     */
    @CsvBindByPosition(position = 4,capture = TRIM)
    private String channelDate;

    /**
     * 交易状态
     */
    @CsvBindByPosition(position = 5,capture = TRIM)
    private String transStat;

    private static final long serialVersionUID = 1L;


}
