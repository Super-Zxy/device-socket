package com.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed
 * 文件名称：StringTools.java
 * 文件描述：字符操作基类
 *
 */
public class StringTools {
    /**
     * 数字式的显示格式
     */
    public static NumberFormat NUM_FMT_INT_FLT = NumberFormat
            .getNumberInstance();// new DecimalFormat("##,###,###.##");

    public static NumberFormat NUM_FMT_PER = NumberFormat.getPercentInstance();

    public static Pattern INTEGER = Pattern.compile("\\d*");

    public static Pattern NUMBER = Pattern.compile("^\\d+[.\\d]?\\d*$");

    /**
     * @see String#valueOf(Object)
     */
    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 检查对象是否为空，如果为空就返回空字符串
     *
     * @param strValue
     * @return 如果为空返回“”，否则返回strValue;
     */
    public static String notNull(String strValue) {
        return notNull(strValue, "");
    }

    /**
     * 检查对象是否为空，如果为空就返回指定默认的值
     *
     * @param strValue
     *            检查字符串对象
     * @param strDefault
     *            默认值
     * @return 返回相应值
     */
    public static String notNull(String strValue, String strDefault) {
        if (strValue == null) {
            return strDefault;
        }
        return strValue;
    }

    /**
     * 将String转换为整形
     *
     * @param inputNumber
     * @param defaultNum
     * @return int
     */
    public static int parseInt(String inputNumber, int defaultNum) {
        if (inputNumber == null)
            return defaultNum;
        int temp = defaultNum;
        try {
            temp = Integer.parseInt(inputNumber.trim());
        } catch (Exception ex) {
            temp = defaultNum;
        }
        return temp;
    }

    /**
     * 将String转换为Double
     */
    public static double parseDouble(String value, double defaultNum) {
        if (value == null || "".equals(value)) {
            return defaultNum;
        }
        double temp = defaultNum;
        try {
            temp = Double.parseDouble(value);
        } catch (Exception ex) {
            temp = defaultNum;
        }
        return temp;
    }

    /**
     * 将String转换为Long
     */
    public static long parseLong(String value, long defaultNum) {
        if (value == null || "".equals(value)) {
            return defaultNum;
        }
        long temp = defaultNum;
        try {
            temp = Long.parseLong(value);
        } catch (Exception ex) {
            temp = defaultNum;
        }
        return temp;
    }

    /**
     * 把字符串数组组合成以split为分隔的字符串
     *
     * @param args
     *            字符串数组
     * @param split
     *            分隔字符串
     * @return 返回组合后的字符串
     */
    public static String toString(String[] args, String split) {
        if (args == null)
            return "";
        String strRet = "";
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                strRet = args[i];
            } else {
                strRet += split + args[i];
            }
        }
        return strRet;
    }

    public static String toString(Object[] args, String split) {
        if (args == null)
            return "";
        String strRet = "";
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                strRet = valueOf(args[i]);
            } else {
                strRet += split + args[i];
            }
        }
        return strRet;
    }

    public static String toString(int[] args, String split) {
        if (args == null)
            return "";
        String strRet = "";
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                strRet = String.valueOf(args[i]);
            } else {
                strRet += split + args[i];
            }
        }
        return strRet;
    }

    public static String toString(char[] args, String split) {
        if (args == null)
            return "";
        String strRet = "";
        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                strRet = String.valueOf(args[i]);
            } else {
                strRet += split + args[i];
            }
        }
        return strRet;
    }

    /**
     * 解析出的字符串对象中符合规则的所有对象
     * 如:/test.do?id=$id$&name=$NAME1$&name=$name2$&name=$name3$
     * 解析出:id,NAME1,name2,name3
     *
     * @param parseValue
     *            要解析的字符串对象
     * @param p
     *            要查询的字符串规则表达式
     * @return 返回查询到的所有符合规则的字符串对象
     */
    public static List parseString(String parseValue, Pattern p) {
        List lisRet = new ArrayList();
        Matcher m = p.matcher(parseValue);
        while (m.find()) {
            lisRet.add(m.group(1));
        }
        return lisRet;
    }

    public static List parseString(String parseValue, String p) {
        List lisRet = new ArrayList();
        String str = parseValue;

        if (str.indexOf(p) >= 0) {
            for (; str.indexOf(p) >= 0;) {
                if (str.indexOf(p) == 0) {
                    str = str.substring(1);

                    continue;
                }
                int i = str.indexOf(p);

                String str1 = str.substring(0, i);

                lisRet.add(str1);
                str = str.substring(i + 1);
                if (str.indexOf(p) < 0)
                    lisRet.add(str);
            }
        }
        return lisRet;
    }

    /**
     * 把传入的<code>value</code>格式成百分率的格式，同时保留2位小数（4舍5入）
     *
     * @param value
     *            格式数值
     * @return 返回数值的百分率格式,如果不是数字,则返回原数据
     */
    public static String toPercentString(String value) {
        return toPercentString(value, 2, 2);
    }

    public static String toPercentString(String value, int maxFraction,
                                         int minFraction) {
        try {
            double dValue = Double.parseDouble(value);
            NUM_FMT_PER.setMaximumFractionDigits(maxFraction);
            NUM_FMT_PER.setMinimumFractionDigits(minFraction);
            value = NUM_FMT_PER.format(dValue);
        } catch (Exception e) {
            return value;
        }
        return value;
    }

    /**
     * 格式化数字显示格式
     *
     * @param formatNum
     *            当前格式化的数字
     * @param maxDigit
     *            格式返回的小数位数
     * @return 返回格式化后的数字
     */
    public static String format(double formatNum, int maxDigit) {
        String value = String.valueOf(formatNum);
        try {
            NUM_FMT_INT_FLT.setMinimumFractionDigits(maxDigit);
            NUM_FMT_INT_FLT.setMaximumFractionDigits(maxDigit);
            value = NUM_FMT_INT_FLT.format(formatNum);
        } catch (Exception e) {
            return value;
        }
        return value;
    }

    public static String format(String formatNum, int maxDigit) {
        if (formatNum == null || formatNum.length() <= 0)
            return formatNum;
        return format(parseDouble(formatNum, 0), maxDigit);
    }

    /**
     * 检查输入的字符串是否是全数字（整数）
     *
     * @param str
     *            输入字符串
     * @return true：是；false：否
     */
    public static boolean isInteger(String str) {
        Matcher m = INTEGER.matcher(str);
        return m.matches();
    }

    /**
     * 检查输入的字符串是否是全数字（小数）
     *
     * @param str
     *            输入字符串
     * @return true：是；false：否
     */
    public static boolean isNumber(String str) {
        Matcher m = NUMBER.matcher(str);
        return m.matches();
    }

    /**
     * 字符编码转换 8859 =》GB2312
     *
     * @param str
     * @return
     */
    public static String getGB2312By8859(String str) {

        try {
            String temp_p = str;
            byte[] temp_t = temp_p.getBytes("ISO8859_1");
            String temp = new String(temp_t, "GB2312");
            return temp;
        } catch (Exception e) {
            str = "";
        }

        return str;
    }

    /**
     * 字符编码转换 GB2312 =》8859
     *
     * @param str
     * @return
     */
    public static String get8859ByGBK(String str) {

        try {
            String temp_p = str;
            byte[] temp_t = temp_p.getBytes("GB2312");
            String temp = new String(temp_t, "ISO8859_1");
            return temp;
        } catch (Exception e) {
            str = "";
        }

        return str;
    }

}
