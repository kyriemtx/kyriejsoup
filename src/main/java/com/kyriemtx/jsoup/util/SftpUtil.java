package com.kyriemtx.jsoup.util;

import com.jcraft.jsch.*;
import com.kyriemtx.jsoup.common.Constants;
import com.kyriemtx.jsoup.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @ClassName SftpUtil
 * @Description SFTP工具类
 * @Author tengxiao.ma
 * @Date 2020/8/18 10:25
 **/

@Slf4j
public class SftpUtil {

    private ChannelSftp channelSftp;

    /**
     * 创建文件
     * @param fileName
     * @return
     */
    public File createFile(String fileName){
        //存放路径
       String localPath = Constants.LOCAL_PATH;
        //创建文件夹
       File file = new File(localPath);
       file.mkdirs();
       //文件前缀
       String per = Constants.FILE_PRE;
       //文件全路径
       File downFile = new File(file,per+fileName+Constants.FILE_LAST);
       if(downFile.exists()){
           downFile.delete();
       }
       return downFile;
    }


    /**
     * 连接SFTP文件服务器
     * @return
     */
    public ChannelSftp connect(){
        String sftpPwd = Constants.SFTP_PWD;
        String sftpUser = Constants.SFTP_USER;
        String sftpHost = Constants.SFTP_HOST;
        int port = Integer.valueOf(Constants.SFTP_PORT);
        try {
            JSch jsch = new JSch();
            jsch.getSession(sftpUser, sftpHost, port);
            Session sshSession = jsch.getSession(sftpUser, sftpHost, port);
            sshSession.setPassword(sftpPwd);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect(30000);
            Channel channel = sshSession.openChannel("sftp");
            channel.connect(10000);
            channelSftp = (ChannelSftp) channel;
            log.info("【连接SFTP服务器：{}成功】",sftpHost);
            return channelSftp;
        }catch (Exception e){
            log.info("【连接SFTP文件服务器异常】，异常信息：{}",e.getMessage());
            return null;
        }
    }


    /**
     * 从SFTP文件服务器下载文件
     * @param channelSftp
     * @param transDate
     * @return
     */
    public File downFromSftp(ChannelSftp channelSftp,String transDate) throws Exception {
        log.info("【开始从文件服务器下载文件】");
        //SFTP服务器上文件存的路径
        String sftpPath = Constants.SFTP_FILE_PATH;
        //SFTP服务器上存放的文件名
        String sftpFileName = Constants.FILE_PRE+transDate+Constants.FILE_LAST;
        if(StringUtils.isNotEmpty(sftpPath)){
            channelSftp.cd(sftpPath);
        }
        //在本地穿件一个文件用来存放目标下载
        File localFileName = createFile(transDate);
        FileOutputStream fileOutputStream = new FileOutputStream(localFileName);
        try {
            channelSftp.get(sftpFileName,fileOutputStream);
            log.info("【从SFTP文件服务器下载文件成功】");
        }catch (BizException e) {
            log.info("【从SFTP文件服务器下载文件失败】",e.getMessage());
            throw new BizException(e.getCode(),"从SFTP文件服务器下载文件失败");
        }
        return localFileName;
    }


    /**
     * 上传本地文件到SFTP文件服务器
     * @param fileName
     * @return
     */
    public static boolean uploadToSftp(String fileName) {
        //上传文件远程存放全路径
        String remoteFileName = Constants.SFTP_UPLOAD_PATH + fileName;
        //待上传文件本地存放全路径
        String localFileName = Constants.CREATE_LOCAL_PATH + fileName;
        String ftpPort = Constants.SFTP_PORT;
        String ftpUser = Constants.SFTP_USER;
        String ftpPwd = Constants.SFTP_PWD;
        String ftpHost = Constants.SFTP_HOST;
        JSch jsch = new JSch();
        Session session;
        String key = "";
        try {
            if (!StringUtils.isBlank(key)) {
                jsch.addIdentity(key);
            }
            if (StringUtils.isBlank(ftpPort)) {
                session = jsch.getSession(ftpUser, ftpHost);
                log.info("连接sftp【{}:{}】 ，用户【{}】", new Object[]{ftpHost, ftpPort, ftpUser});
            } else {
                session = jsch.getSession(ftpUser, ftpHost,
                        Integer.parseInt(ftpPort));
                log.info("连接sftp【{}:{}】 ，用户【{}】", new Object[]{ftpHost, ftpPort, ftpUser});
            }
            if (!StringUtils.isBlank(ftpPwd)) {
                session.setPassword(ftpPwd);
                log.debug("sftp设置密码:【{}】", ftpPwd);
            }
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);
            log.info("连接sftp【{}:{}】成功", new Object[]{ftpHost, ftpPort});
            Channel channel = session.openChannel("sftp");
            channel.connect(10000);
            ChannelSftp sftp = (ChannelSftp) channel;
            log.info("当前目录【{}】", sftp.pwd());
            try {
                sftp.cd(remoteFileName);
            } catch (SftpException e) {
                log.info("{}目录不存在，创建目录", remoteFileName);
                sftp.mkdir(remoteFileName);
            }
            try {
                sftp.put(localFileName, remoteFileName);
            } catch (Exception e) {
                log.error("文件【{}】放到sftp目录【{}】失败", localFileName, remoteFileName);
                log.error("异常原因：", e);
                return false;
            }
        } catch (Exception e) {
            log.error("sftp 连接失败", e);
            return false;
        }
        return true;
    }


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


}
