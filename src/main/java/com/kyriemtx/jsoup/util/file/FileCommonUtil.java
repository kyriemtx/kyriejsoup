package com.kyriemtx.jsoup.util.file;

import com.kyriemtx.jsoup.common.Constants;
import com.kyriemtx.jsoup.common.RespCodeEnum;
import com.kyriemtx.jsoup.exception.BizException;
import com.kyriemtx.jsoup.project.entity.model.TransLog;
import com.kyriemtx.jsoup.util.StringUtil;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName FileParseUtil
 * @Description 文件工具类
 * @Author tengxiao.ma
 * @Date 2020/8/18 13:20
 **/

@Slf4j
public class FileCommonUtil {

    /**
     * 解压ZIP文件
     * @param zipFileName  压缩包文件名
     * @param unPath     解压到的目标路径
     * @param password   解压密码
     */
    public void unzip(String zipFileName, String unPath, String password) {
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            zipFile.setFileNameCharset("GBK");
            if (!zipFile.isValidZipFile()) {
                throw new ZipException("压缩文件不合法,可能被损坏");
            }
            File destDir = new File(unPath);
            if (destDir.isDirectory() && !destDir.exists()) {
                destDir.mkdir();
            }
            //当压缩包需要密码来解压时，在此设置解压密码
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());
            }
            zipFile.extractAll(unPath);
        } catch (Exception e) {
            throw new BizException("解压zip文件失败");
        }
    }


    /**
     * 解析文件方法一
     * @param fileName
     */
    public static List  parseFile(String fileName) {
        log.info("【开始解析文件：{}】", fileName);
        String encoding = "GBK";
        String localPath = Constants.LOCAL_PATH;
        String fileFullName = localPath + fileName;
        log.info("【文件本地存放路径：{}】", fileFullName);
        File file = new File(fileFullName);
        if (!file.exists()) {
            log.error("【文件：{}不存在】", fileFullName);
            throw new BizException(RespCodeEnum.FILE_NOT_EXIST);
        }
        FileInputStream fileInputStream = null;
        List<TransLog> transLogs = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(fileFullName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
            CSVReader csvReader = new CSVReader(inputStreamReader);
            String[] record = null;
            while ((record = csvReader.readNext()) != null){
                if (StringUtil.isChinese(record[0])) {
                    log.info("【文件解析-------表头不做处理】");
                } else {
                    TransLog transLog = new TransLog();
                    transLog.setReqSeqId(record[0]);
                    transLog.setAmount(new BigDecimal(record[1]));
                    transLog.setTransDate(record[2]);
                    transLog.setChannelStat(record[3]);
                    transLog.setChannelDate(record[4]);
                    transLog.setTransStat(record[5]);
                    transLogs.add(transLog);
                }
            }
            return transLogs;
        } catch (Exception e) {
            log.error("解析文件:{}失败,异常信息{}", fileName, e);
            throw new BizException(RespCodeEnum.FILE_PARSE_FAILURE);
        } finally {
            log.info("【解析文件】解析结果：{}", transLogs);
            IOUtils.closeQuietly(fileInputStream);
        }
    }


    /**
     * 解析文件方法2
     * @param fileName
     * @return
     */
    public static List parseFile2(String fileName){
        List<TransLog> transLogs = new ArrayList<>();
        String localPath = Constants.LOCAL_PATH;
        String fileFullName = localPath + fileName;
        try(FileInputStream fis = new FileInputStream(fileFullName);
            InputStreamReader inputStream = new InputStreamReader(fis,"utf-8")){
            if(StringUtils.isNotBlank(fileName)){
                log.info("开始解析文件,文件名:{}", fileName);
                CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(inputStream);
                CSVParserBuilder csvParserBuilder = new CSVParserBuilder();
                csvParserBuilder.withSeparator('|');
                csvReaderBuilder.withCSVParser(csvParserBuilder.build());
                CSVReader reader = csvReaderBuilder.build();
                ColumnPositionMappingStrategy<TransLog> mapper = new ColumnPositionMappingStrategy<>();
                mapper.setType(TransLog.class);
                CsvToBean<TransLog> csvToBean = new CsvToBean<>();
                //CSVToBeanFilter csvToBeanFilter = new CSVToBeanFilter();
                csvToBean.setCsvReader(reader);
                //csvToBean.setFilter(csvToBeanFilter);
                csvToBean.setMappingStrategy(mapper);
                Iterator<TransLog> iterator = csvToBean.iterator();
                while (iterator.hasNext()){
                    TransLog transLog = iterator.next();
                    transLogs.add(transLog);
                }
                log.info("文件:{} 解析完成",fileName);
            }else {
                log.info("文件:{} 不能为空，请确认",fileName);
            }
        }catch (Exception e){
            log.info("文件：{} 解析异常结束",e);
            throw new BizException(RespCodeEnum.FILE_PARSE_FAILURE);
        }
        return transLogs;
    }



    public static void main(String[] args) {
        String fileName = "ubmp_amphit_TOP_20200323.csv";
        List<TransLog> transLogs = parseFile2(fileName);
    }

}
