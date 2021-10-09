package com.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * Created by Administrator on 2018/12/18.
 */
public class DateUtil {

    /**
     * 获取本账期最后一刻
     * @param billMonth
     * @return
     */
    public static String getMonthMaxTime(String billMonth)
    {
        Calendar cal = Calendar.getInstance();

        cal.setTime(timeStr2Date(billMonth,"yyyyMM"));

        int maxDay = cal.getActualMaximum(Calendar.DATE);

        cal.set( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), maxDay, 23, 59, 59);

        return date2TimeStr(cal.getTime(),"yyyyMMddHHmmss");
    }

    /**
     * 获取当前时间的年月
     *
     * @return 当前年月的整数
     */
    public static int getYearAndmonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");

        return Integer.parseInt(format.format(new Date()));
    }


    /**
     * 获取指定格式的当前日期
     *
     * @param pattern，日期格式，如:yyyyMMddHHmmss 格式描述符的含义参见JDK simpleDateFormat类
     * @return String
     */
    public static String getCurrentDate(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("input string parameter is null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date now = new Date();
        return sdf.format(now);
    }

    /**
     * 将字符串转换成日期形
     *
     * @param time    日期
     * @param pattern 转换类型
     * @return Date
     */
    public static Date timeStr2Date(String time, String pattern) {
        if (time == null) {
            throw new IllegalArgumentException("time parameter can not be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern parameter can not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            throw new IllegalArgumentException("using [" + pattern + "] parse [" + time + "] failed");
        }
    }

    /**
     * 比较两个事件
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean compare(String startTime, String endTime) {
        //转化成时间
        long startTimeLong=Long.parseLong(DateUtil.date2TimeStr(DateUtil.timeStr2Date(startTime,"yyyy-MM-dd HH:mm:ss"),"yyyyMMddHHmmss"));
        long endTimeLong=Long.parseLong(DateUtil.date2TimeStr(DateUtil.timeStr2Date(endTime,"yyyy-MM-dd HH:mm:ss"),"yyyyMMddHHmmss"));

        return startTimeLong <= endTimeLong;
    }

    public static String calTime(String time, int type, int addDate) throws Exception{
        String rtnTime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(sdf.parse(time));

        //增加小时
        if (type == 1) {
            calendar.add(Calendar.HOUR_OF_DAY, addDate);
        } else if (type == 2) {
            //增加分钟
            calendar.add(Calendar.MINUTE, addDate);
        } else if (type == 3) {
            //增加秒
            calendar.add(Calendar.SECOND, addDate);
        }

        Date date = calendar.getTime();

        rtnTime = date2TimeStr(date, "yyyy-MM-dd HH:mm:ss");

        System.out.println(rtnTime);

        return rtnTime;
    }

    public static Date calDay(Date date,int day){
        Calendar calendar = Calendar.getInstance();//得到一个Calendar的实例
        calendar.setTime(date); //设置时间为当前时间
        calendar.add(Calendar.DATE, -day); //31天前

        return calendar.getTime();
    }

    /**
     * 将时间转换成Date型
     *
     * @param time    时间
     * @param pattern 转换类型
     * @return String
     */
    public static String date2TimeStr(Date time, String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("pattern parameter can not be null");
        }
        if (time == null) {
            throw new IllegalArgumentException("time parameter can not be null");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(time);
    }

    /**
     * 将字符串转换成日期形 默认格式yyyyMMddHHmmss
     *
     * @param time
     * @return
     */
    public static Date timeStr2Date(String time) {
        if (time == null) {
            throw new IllegalArgumentException("time parameter can not be null");
        }
        return timeStr2Date(time, "yyyyMMddHHmmss");
    }

    public static int get_monthnumber13(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int base = 0;
        return (calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH) + 1 + base) % 13 + 1;
    }

    public static int get_monthnumber2(int yearMonth) {
        return (yearMonth % 2) + 1;
    }

    public static int get_monthnumber13(int yearMonth) {
        return ((yearMonth / 100) * 12 + (yearMonth % 100)) % 13 + 1;
    }

    public static int getDaysOfMonth(int yearMonth) {
        char[] daysOfMonth = new char[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int year = yearMonth / 100;
        int month = yearMonth % 100;
        if (((year % 4) == 0 && (year % 100) != 0) || (year % 400) == 0) {
            daysOfMonth[1] = 29;
        }

        return daysOfMonth[month - 1];
    }

    /**
     * 当月最后一秒
     *
     * @param yearMonth
     * @return
     */
    public static String getLastTimeOfMonth(int yearMonth) {
        return String.valueOf(yearMonth) + DateUtil.getDaysOfMonth(yearMonth) + "235959";
    }

    /**
     * 当月开始第一秒
     *
     * @param yearMonth
     * @return
     */
    public static String getStartTimeOfMonth(int yearMonth) {
        return String.valueOf(yearMonth) + "01000000";
    }

    //根据时长确定显示年月
    public static int getDurationMonth(String duration) {
        int yearAndmonth = DateUtil.getYearAndmonth();
        int durationMonth = 0;
        if (yearAndmonth % 100 >= Integer.parseInt(duration)) {
            durationMonth = yearAndmonth - Integer.parseInt(duration) + 1;
        } else {
            int year = (yearAndmonth / 100) - 1;
            int month = 13 - (Integer.parseInt(duration) - yearAndmonth % 100);
            durationMonth = year * 100 + month;
        }
        return durationMonth;
    }

    // 增加小时
    public static String addHour(String strTime, int delta) {
        strTime = timeTransform(strTime, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        Date dDate = null;
        try {
            dDate = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(dDate);

        cal.add(Calendar.HOUR_OF_DAY, delta);

        return timeTransform(formatter.format(cal.getTime()), "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
    }

    //将时间按照新格式展示：time 时间，oldpattern 旧日期格式，newpattern 新日期格式。日期格式描述符的含义参见JDK simpleDateFormat类
    public static String timeTransform(String time, String oldpattern, String newpattern) {
        // 打印调试信息
        String oldtime;
        if (oldpattern == null) {
            throw new IllegalArgumentException("the old pattern is null");
        }
        if (newpattern == null) {
            throw new IllegalArgumentException("the new pattern is null");
        }
        SimpleDateFormat olddatepattern = new SimpleDateFormat(oldpattern);
        Date now;
        try {
            now = olddatepattern.parse(time);
            // 用原来的pattern解析日期，再和原来的比较，由此来检查时间是否合法
            oldtime = olddatepattern.format(now);
            if (!oldtime.equals(time)) {
                throw new IllegalArgumentException("using Illegal time");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("using [" + oldpattern + "] parse [" + time + "] failed");
        }
        SimpleDateFormat newdatepattern = new SimpleDateFormat(newpattern);

        return newdatepattern.format(now);
    }

    public static int get_monthnumber7(int yearMonth) {
        int base = 1;
        return ((yearMonth / 100) * 12 + (yearMonth % 100) + base) % 7 + 1;
    }

    public static int get_monthnumber4(int yearMonth) {
        int base = 1;
        return ((yearMonth / 100) * 12 + (yearMonth % 100) + base) % 4 + 1;

    }

    public static void main(String[] args) {
        try {
            calTime("2020-10-04 11:40:20",1,13);

//            String s="01";
//            System.out.println(s.substring(1));

            System.out.println(DateUtil.getCurrentDate("yyyyMM"));

            System.out.println(DateUtil.get_monthnumber13(Integer.parseInt(DateUtil.getCurrentDate("yyyyMM"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
