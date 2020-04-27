package com.qmt.okhttplibrary.network.utils;

import java.text.DecimalFormat;

/**
 *
 * Created by wlk on 2016/12/19.
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     * @param str 需要判断的字符串
     * @return true代表传入的字符串为空,false代表不为空;
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.replace(" ", "").length() == 0
                || str.equals("null") || str.equals("NULL");
    }


    /**
     * 首字母大写
     *
     * @param str 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String str) {
        if (isEmpty(str) || !Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        return String.valueOf((char) (str.charAt(0) - 32)) + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String str) {
        if (isEmpty(str) || !Character.isUpperCase(str.charAt(0))) {
            return str;
        }
        return String.valueOf((char) (str.charAt(0) + 32)) + str.substring(1);
    }

    /**
     * 反转字符串
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        if(isEmpty(s)) {
            return s;
        }
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }


    /**
     * 将字符串格式化成long型；
     * @param str 需要格式化的字符串
     * @return 格式化后的结果，如果格式化错误返回0；
     */
    public static long parseLong(String str){
        long l = 0;
        if(isEmpty(str)) {
            return l;
        } else {
            try {
                l = Long.parseLong(str);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return l;
    }

    /**
     * 将字符串格式化成double类型;
     * @param str 需要格式化的字符串
     * @return 格式化后的结果,如果字符串不是double类型返回0.0;
     */
    public static double parseDouble(String str){
        double d=0;
        if(isEmpty(str)) {
            return d;
        } else {
            try {
                d = Double.parseDouble(str);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return d;
    }

    /**
     * 四舍五入保留小数;
     * @param d 需要格式化的double值
     * @param i 保留几位小数,
     * 			0-->不保留小数
     * 			1-->保留1位小数
     * 			2-->保留2位小数
     * 			3-->保留三位小数
     * 			4-->保留四位小数
     * @return 格式化后的结果,string类型;
     */
    public static String formatDouble(double d, int i){
        DecimalFormat df;

        switch (i) {
            case 0:
                df = new DecimalFormat("#");
                break;
            case 1:
                df = new DecimalFormat("#0.0");
                break;
            case 2:
                df = new DecimalFormat("#0.00");
                break;
            case 3:
                df = new DecimalFormat("#0.000");
                break;
            case 4:
                df = new DecimalFormat("#0.0000");
                break;
            default:
                df = new DecimalFormat("#.####");
                break;
        }
        return df.format(d);
    }

    /**
     * 只含数字
     *
     * @param data 可能只包含数字的字符串
     * @return 是否只包含数字
     */
    public static boolean isNumber(String data) {
        if(isEmpty(data)) {
            return false;
        }
        if(data.length() != 11) {
            return false;
        }
        String expr = "^[0-9]+$";
        return data.matches(expr);
    }


    /**
     * 判断密码是否为8~16位
     *
     * @param paw 密码
     * @return 是否只包含数字
     */
    public static boolean isSetPaw(String paw) {
        if(isEmpty(paw)) {
            return false;
        }
        if(paw.length()<8 || paw.length()>=16){
            return false;
        }
        return true;
    }



}
