package com.kyriemtx.jsoup.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringUtil
 * @Description  字符工具类
 * @Author tengxiao.ma
 * @Date 2020/8/18 13:36
 **/
public class StringUtil {

    /**
     * 空字符串
     */
    public static final String EMPTY_STR = "";


    public static char c1 = 0x0001;
    public static char c2 = 0x007e;
    public static char c3 = 0xff60;
    public static char c4 = 0xff9f;




    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static boolean isNull(String input) {
        if (input == null || input.trim().length() == 0) {
            return true;
        }
        return false;
    }











    /**
     * 判断字符串是否非空（“”）或者不为NULL
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        return !StringUtil.isNull(str);
    }

    /**
     * 向左补足字符串至指定长度
     *
     * @param str
     * @param len
     * @param ch
     * @return
     */
    public static String leftPad(String str, int len, char ch) {
        StringBuffer sb = new StringBuffer(len);
        int p = len - str.length();
        for (int i = 0; i < len; i++) {
            if (i < p) {
                sb.append(ch);
            } else {
                sb.append(str.charAt(i - p));
            }
        }
        return sb.toString();
    }

    /**
     * 向右补足字符串至指定长度
     *
     * @param str
     * @param len
     * @param ch
     * @return
     */
    public static String rightPad(String str, int len, char ch) {
        byte[] byteStr = str.getBytes();
        int p = len - byteStr.length;
        String nstr = "";

        for (int i = 0; i < p; i++) {
            nstr = nstr + ch;
        }
        return str + nstr;
    }

    /**
     * 字符串前后去空格（可兼容null值）
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    /**
     * 返回指定字符串的值。
     * <p>
     * 如果指定字符串为null或空格，则返回默认值
     * </P>
     *
     * @param value
     * @param defValue
     * @return
     */
    public static String getStringByDefValue(String value, String defValue) {
        if (isNull(value)) {
            return defValue;
        }
        return value.trim();
    }

    /**
     * 使用指定分隔符，将字符串转换为字符串集合
     *
     * @param input
     * @param splitChar
     * @return
     */
    public static List<String> stringToList(String input, String splitChar) {
        List<String> list = new ArrayList<String>();
        String[] arrays = input.split(splitChar);
        for (int i = 0; i < arrays.length; i++) {
            list.add(arrays[i]);
        }
        return list;
    }

    /**
     * 使用逗号，将字符串集合拼接为字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        // 拼接字符串
        StringBuffer sb = new StringBuffer(128);
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(",");
        }
        // 截取最后一位
        if (sb.length() > 1) {
            return sb.substring(0, (sb.length() - 1));
        } else {
            return "";
        }
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString();
    }

    /**
     * 判断字符串数组中是否包含指定字符串
     *
     * @param array
     * @param str
     * @return
     */
    public static boolean contains(String[] array, String str) {
        if (isNull(str)) {
            return false;
        }
        str = trim(str);
        boolean ret = false;
        for (int i = 0; i < array.length; i++) {
            if (str.equals(array[i])) {
                ret = true;
                break;
            }
        }
        return ret;
    }


    /**
     * 判断字符是否为汉字字符
     *
     * @param c
     * @return
     */
    public static boolean isChineseChar(char c) {
        boolean res = (c >= c1 && c <= c2) || (c3 <= c && c <= c4);
        if (res) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串长度，当为汉字时，以2个字节计算
     *
     * @param str
     * @return
     */
    public static int strlen(String str) {
        int len = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean res = (c >= c1 && c <= c2) || (c3 <= c && c <= c4);
            if (res) {
                len++;
            } else {
                len += 2;
            }
        }
        return len;
    }

    /**
     * 字符串长度验证
     *
     * @param str
     * @param i
     * @return
     */
    public static final boolean isStrLenEqual(String str, int i) {
        return ((isNotNull(str)) && (str.trim().length() == i));
    }

    /**
     * 将null字符串转换为空字符串
     *
     * @param str
     * @return
     */
    public static String null2Empty(String str) {
        return isNull(str) ? EMPTY_STR : str;
    }

    /**
     * 驼峰格式转换为下划线
     * 例：appKey 转换为 app_key
     *
     * @param camelCaseName 驼峰格式
     * @return 下划线格式
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * 将字符串以split分隔，并且添加到List中
     *
     * @param str
     * @param split
     * @return
     */
    public static List<String> transStringToList(String str, String split) {
        if (StringUtils.isNotBlank(str)) {
            String[] strings = str.split(split);
            List<String> stringList = new ArrayList<>();
            for (String string : strings) {
                stringList.add(string);
            }
            return stringList;
        } else {
            return null;
        }
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static final String chars = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMabcdefghijklmnopqrstuvwxyz";

    public static String random(int num) {
        StringBuffer value = new StringBuffer();
        for (int i = 0; i < num; i++) {
            value.append(chars.charAt((int) (Math.random() * 62)));
        }
        return value.toString();
    }


    /** 费率公式替换 匹配模式 **/
    public static final String pattern = "[*][0-9]+.[0-9]+";
    /** 替换费率公式 **/
    public static  String afterReplace(String input,String replaceStr){
        if(StringUtils.isBlank(input)){
            return input;
        }
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        return m.replaceAll(replaceStr);
    }

    /** 判断字符串是否包含中文 **/
    public static final String pattern1 = "[\u4e00-\u9fa5]";
    public static boolean isChinese(String input){
        if(StringUtils.isBlank(input)){
            return false;
        }
        Pattern r = Pattern.compile(pattern1);
        Matcher m = r.matcher(input);
        if(m.find()){
            return true;
        }
        return false;
    }




    public static void main(String args[]) {
        String str = "MTM(40.01,999.99,amount*20.00006)";
        String ss = afterReplace(str,"*0.0040");

        String chac = "sfdfsaffa";
        boolean b = isChinese(chac);
        String ssss = "年末";
        boolean cc = isChinese(ssss);
    }

}
