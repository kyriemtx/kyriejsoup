package com.kyriemtx.jsoup.common;

/**
 * @ClassName Constants
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/13 15:47
 **/
public class Constants {

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";


    /** 下载文件本地存放路径 **/
    public static final String LOCAL_PATH = "D:\\浏览器下载\\";
    /** 本地生成文件存放路径 **/
    public static final String CREATE_LOCAL_PATH = "D:\\浏览器下载\\本地生成文件\\";
    /**  文件名前缀 **/
    public static final String FILE_PRE = "KYRIE_";
    /**  文件名后缀 **/
    public static final String FILE_LAST = ".csv";

    /**  SFTP地址 **/
    public static final String SFTP_HOST = "sftpHost";
    /**  SFTP端口 **/
    public static final String SFTP_PORT = "sftpPort";
    /**  SFTP用户名 **/
    public static final String SFTP_USER = "sftpUser";
    /**  SFTP密码 **/
    public static final String SFTP_PWD = "sftpPwd";
    /** 解压密码 **/
    public static final String ZIP_PWD = "zipPassword";
    /**SFTP服务器待下载文件存放路径**/
    public static final String SFTP_FILE_PATH = "sftpFilePath";

    /**SFTP服务器待下载文件存放路径**/
    public static final String SFTP_UPLOAD_PATH = "sftpUploadFilePath";


}
