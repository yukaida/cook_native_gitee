package com.ebanswers.kitchendiary.widget.datepicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 说明：日期格式化工具
 * 作者：liuwan1992
 * 添加时间：2018/12/17
 * 修改人：liuwan1992
 * 修改时间：2018/12/18
 */
public class DateFormatUtils {

    private static final String DATE_FORMAT_PATTERN_YM = "yyyy-MM";
    private static final String DATE_FORMAT_PATTERN_YMD = "yyyy-MM-dd";
    private static final String DATE_FORMAT_PATTERN_YMD_HM = "yyyy-MM-dd HH:mm";
    private static final String DATE_FORMAT_PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static final int YMD_HM = 0;
    public static final int YMD = 1;
    public static final int YM = 2;
    public static final int YMD_HMS = 3;

    /**
     * 时间戳转字符串
     *
     * @param timestamp     时间戳
     * @param isPreciseTime 是否包含时分
     * @return 格式化的日期字符串
     */
    public static String long2Str(long timestamp, int isPreciseTime) {
        return long2Str(timestamp, getFormatPattern(isPreciseTime));
    }

    private static String long2Str(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 字符串转时间戳
     *
     * @param dateStr       日期字符串
     * @param isPreciseTime 是否包含时分
     * @return 时间戳
     */
    public static long str2Long(String dateStr, int isPreciseTime) {
        return str2Long(dateStr, getFormatPattern(isPreciseTime));
    }

    private static long str2Long(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.CHINA).parse(dateStr).getTime();
        } catch (Throwable ignored) {
        }
        return 0;
    }

    private static String getFormatPattern(int showSpecificTime) {
        if (showSpecificTime == YMD_HM) {
            return DATE_FORMAT_PATTERN_YMD_HM;
        } else if (showSpecificTime == YMD){
            return DATE_FORMAT_PATTERN_YMD;
        } else if (showSpecificTime == YMD_HMS){
            return DATE_FORMAT_PATTERN_YMD_HMS;
        }else {
            return DATE_FORMAT_PATTERN_YM;
        }
    }

}
