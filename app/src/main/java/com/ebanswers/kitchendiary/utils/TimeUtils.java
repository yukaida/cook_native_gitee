package com.ebanswers.kitchendiary.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换工具类
 */
public class TimeUtils {

    private static String year;
    private static String month;
    private static String day;

    public static String longToDateString(long unixDate, String dateformat) {
        if(unixDate>0){
            String date = new SimpleDateFormat(dateformat).format(new Date(unixDate*1000));
            return date;
        }else{
            return "";
        }
    }

    /**
     * long类型转换成yyyy-MM-dd  HH:mm
     * @param unixDate
     * @return
     */
    public static String stringToDateString(String unixDate) {
        if(unixDate != null){
            long time = Long.parseLong(unixDate)*1000;
            String date = new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(new Date(time));
            return date;
        }else{
            return "";
        }
    }

    /**
     * long类型转换成yyyy-MM-dd
     * @param unixDate
     * @return
     */
    public static String stringToDate(String unixDate) {
        if(unixDate != null){
            long time = Long.parseLong(unixDate)*1000;
            String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date(time));
            return date;
        }else{
            return "";
        }
    }

    /**
     * long类型转换成日期格式
     * @param unixDate
     * @param dateformat
     * @return
     */
    public static String stringToDateString(String unixDate, String dateformat) {
        if(unixDate != null){
            long time = Long.parseLong(unixDate)*1000;
            String date = new SimpleDateFormat(dateformat).format(new Date(time));
            return date;
        }else{
            return "";
        }
    }

    /**
     * yyyy-MM-dd转换成long
     * @param date
     * @return
     */
    public static long dateStringToLong(String date) {
        long time =0l;
        try{
            if(date!=null&&!date.equals("")){
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                time = sf.parse(date).getTime()/1000;
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return time;
    }

    public static int getCurrentYear(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;
    }

    public static int getCurrentMonth(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        return month;
    }

    public static int getCurrentDay(){
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.DAY_OF_MONTH);
        return month;
    }

    public static String parseData(String date){
        year = date.substring(0, 4);
        month = date.substring(4, 6);
        day = date.substring(6);
        return year + "-"+ month +"-"+ day;
    }

    /**
     * 返回当前时间的格式为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String  getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(System.currentTimeMillis());
    }

    //毫秒转秒
    public static String long2String(long time){

        //毫秒转秒
        int sec = (int) time / 1000 ;
        int min = sec / 60 ;	//分钟
        sec = sec % 60 ;		//秒
        if(min < 10){	//分钟补0
            if(sec < 10){	//秒补0
                return "0"+min+":0"+sec;
            }else{
                return "0"+min+":"+sec;
            }
        }else{
            if(sec < 10){	//秒补0
                return min+":0"+sec;
            }else{
                return min+":"+sec;
            }
        }

    }



}
