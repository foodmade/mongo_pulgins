package com.generate.utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * 第一个字母变小写
     * @param str
     * @return
     */
    public static String getLowercaseChar(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }

    /**
     * 第一个字母变大写
     * @param str
     * @return
     */
    public static String getCapitalcaseChar(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    /**
     * 获取路径的最后面字符串<br>
     * 如：<br>
     *     <code>str = "com.b510.base.bean.User"</code><br>
     *     <code> return "User";<code>
     * @param str
     */
     public static String getLastChar(String str) {
         if ((str != null) && (str.length() > 0)) {
             int dot = str.lastIndexOf('.');
             if ((dot > -1) && (dot < (str.length() - 1))) {
                 return str.substring(dot + 1);
             }
         }
         return str;
     }

    public static <T> boolean isEmpty(T arg){
        try {
            return Optional.of(arg)
                    .map(Object::toString)
                    .map(String::trim)
                    .map(c -> c.equals(""))
                    .map(b -> b || arg.equals("null"))
                    .map(a -> a || arg.equals("undefined"))
                    .orElse(false);
        } catch (Exception e) {
            return true;
        }
    }

    public static <T> boolean isEmpty(T...args){
        for (T t:args){
            if(isEmpty(t)){
                return true;
            }
        }
        return false;
    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

    /**
     *    字符串的长度 0.0.0.0 7位 ~ 000.000.000.000 15位
     *    将字符串拆分成四段
     *    检查每段是否都是纯数字
     *    检查每段是否都在0-255之间
     *    以上条件都满足的话返回true
     */
    public static boolean isIPAddress(String str) {
        // 如果长度不符合条件 返回false
        if(str.length()<7 || str.length() >15) return false;
        String[] arr = str.split("\\.");
        //如果拆分结果不是4个字串 返回false
        if( arr.length != 4 )    return false;
        for(int i = 0 ; i <4 ; i++ ){
            for(int j = 0; j<arr[i].length();j++){
                char temp = arr[i].charAt(j);
                //如果某个字符不是数字就返回false
                if(!( temp>'0' && temp< '9' ) ) return false;
            }
        }
        for(int i = 0 ; i<4;i++){
            int temp = Integer.parseInt( arr[i] );
            //如果某个数字不是0到255之间的数 就返回false
            if( temp<0 || temp >255)    return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为URL
     * @param urls 需要判断的String类型url
     * @return true:是URL；false:不是URL
     */
    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//对比
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    public static String entityJavaModelName(String name) {
        //消除特殊符号
        String regex = "[^0-9a-zA-Z\\u4e00-\\u9fa5.，,。？“”]+";
        return name.replaceAll(regex,"");
    }

    /**
     * 判断是否含有特殊字符
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        if(isEmpty(str)){
            return true;
        }
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
}
