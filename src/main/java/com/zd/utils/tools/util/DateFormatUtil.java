package com.zd.utils.tools.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

    private static DateFormat sdf;

    public static String formatDate(String template, Date date){
        try {
            sdf = new SimpleDateFormat(template);
            return sdf.format(date);
        }catch (Exception e) {
            return null;
        }
    }

    public static Date formatString(String template, String str){
        try {
            sdf = new SimpleDateFormat(template);
            return sdf.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取当天的结束时间
    public static Date getDayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
