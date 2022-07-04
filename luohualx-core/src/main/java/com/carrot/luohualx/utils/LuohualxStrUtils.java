package com.carrot.luohualx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author carrot
 * @date 2022/6/6
 */
public class LuohualxStrUtils {
    private static final Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");
    /**
     * 去掉换行等空白字符串
     * @param str
     * @return
     */
    public static String trimSpace(String str) {
       if(null != str){
           return str.replaceAll("\\s*", "");
       }
       return null;
    }

    /**
     * 下划线转驼峰
     * @param name
     * @return
     */
    public static String underscoreName(String name) {
        Matcher matcher = UNDERLINE_PATTERN.matcher(name);
        StringBuffer sb = new StringBuffer(name);
        if (matcher.find()) {
            sb = new StringBuffer();
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            //把之后的字符串也添加到StringBuffer对象中
            matcher.appendTail(sb);
        } else {
            //去除除字母之外的前面带的下划线
            return sb.toString().replaceAll("_", "");
        }
        return underscoreName(sb.toString());

    }

}
