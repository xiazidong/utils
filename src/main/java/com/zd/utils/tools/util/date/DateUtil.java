package com.zd.utils.tools.util.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Zidong
 */
public class DateUtil {

    // ==格式到年==
    /**
     * 日期格式，年份，例如：2004，2008
     */
    public static final String DATE_FORMAT_YYYY = "yyyy";

    // ==格式到年月 ==
    /**
     * 日期格式，年份和月份，例如：200707，200808
     */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    /**
     * 日期格式，年份和月份，例如：200707，2008-08
     */
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";


    // ==格式到年月日==
    /**
     * 日期格式，年月日，例如：050630，080808
     */
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：06-12-25，08-08-08
     */
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";

    /**
     * 日期格式，年月日，例如：20050630，20080808
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式，年月日，例如：2016.10.05
     */
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";

    /**
     * 日期格式，年月日，例如：2016年10月05日
     */
    public static final String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";


    // ==格式到年月日 时分 ==

    /**
     * 日期格式，年月日时分，例如：200506301210，200808081210
     */
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";

    /**
     * 日期格式，年月日时分，例如：20001230 12:00，20080808 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";

    /**
     * 日期格式，年月日时分，例如：2000-12-30 12:00，2008-08-08 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";


    // ==格式到年月日 时分秒==
    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    /**
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开
     * 例如：2005-05-10 23：20：00，2008-08-08 20:08:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";


    // ==格式到年月日 时分秒 毫秒==
    /**
     * 日期格式，年月日时分秒毫秒，例如：20001230120000123，20080808200808456
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";


    // ==特殊格式==
    /**
     * 日期格式，月日时分，例如：10-05 12:00
     */
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";

    public static Date date = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;


    /**
     * 获取某日期的年份
     *
     * @param date
     * @return
     */
    public static Integer getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取某日期的月份
     *
     * @param date
     * @return
     */
    public static Integer getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取某日期的日数
     *
     * @param date
     * @return
     */
    public static Integer getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取 yyyy-MM-dd HH:mm:ss 格式的当前时间
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        s.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return s.format(new Date());
    }

    /**
     * 格式化Date时间
     *
     * @param time       Date类型时间
     * @param timeFromat String类型格式
     * @return 格式化后的字符串
     */
    public static String parseDateToStr(Date time, String timeFromat) {
        DateFormat dateFormat = new SimpleDateFormat(timeFromat);
        return dateFormat.format(time);
    }

    /**
     * 格式化 Timestamp时间
     *
     * @param timestamp  Timestamp类型时间
     * @param timeFromat
     * @return 格式化后的字符串
     */
    public static String parseTimestampToStr(Timestamp timestamp, String timeFromat) {
        SimpleDateFormat df = new SimpleDateFormat(timeFromat);
        return df.format(timestamp);
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 默认值为当前时间Date
     * @return 格式化后的字符串
     */
    public static String parseDateToStr(Date time, String timeFromat, final Date defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.format(time);
        } catch (Exception e) {
            if (defaultValue != null) {
                return parseDateToStr(defaultValue, timeFromat);
            } else {
                return parseDateToStr(new Date(), timeFromat);
            }
        }
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 默认时间值String类型
     * @return 格式化后的字符串
     */
    public static String parseDateToStr(Date time, String timeFromat, final String defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.format(time);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 格式化String时间
     *
     * @param time       String类型时间
     * @param timeFromat String类型格式
     * @return 格式化后的Date日期
     */
    public static Date parseStrToDate(String time, String timeFromat) throws ParseException {
        if (time == null || "".equals(time)) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(timeFromat);
        return dateFormat.parse(time);
    }

    /**
     * 格式化String时间
     *
     * @param strTime      String类型时间
     * @param timeFromat   String类型格式
     * @param defaultValue 异常时返回的默认值
     * @return
     */
    public static Date parseStrToDate(String strTime, String timeFromat,
                                      Date defaultValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFromat);
            return dateFormat.parse(strTime);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 当strTime为2008-9时返回为2008-9-1 00:00格式日期时间，无法转换返回null.
     *
     * @param strTime
     * @return
     */
    public static Date strToDate(String strTime) throws ParseException {
        if (strTime == null || strTime.trim().length() <= 0) {
            return null;
        }
        Date date = null;
        List<String> list = new ArrayList<>(11);
        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS);
        list.add(DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDD_HH_MI);
        list.add(DATE_TIME_FORMAT_YYYYMMDDHHMISS);
        list.add(DATE_FORMAT_YYYY_MM_DD);
        //list.add(DATE_FORMAT_YY_MM_DD);
        list.add(DATE_FORMAT_YYYYMMDD);
        list.add(DATE_FORMAT_YYYY_MM);
        list.add(DATE_FORMAT_YYYYMM);
        list.add(DATE_FORMAT_YYYY);

        for (String format : list) {
            if (strTime.indexOf("-") > 0 && format.indexOf("-") < 0) {
                continue;
            }
            if (strTime.indexOf("-") < 0 && format.indexOf("-") > 0) {
                continue;
            }
            if (strTime.length() > format.length()) {
                continue;
            }
            date = parseStrToDate(strTime, format);
            if (date != null) {
                break;
            }
        }

        return date;
    }

    /**
     * @return
     */
    public static Map strRageToDate(String str) {
        Map map = new HashMap<String, Date>(2);
        String[] ages = str.split("-");
        int beforeNum = Integer.parseInt(ages[0]);
        int afterNum = Integer.parseInt(ages[1]);
        //获取现在时间
        Calendar mycalendar = Calendar.getInstance();
        mycalendar.add(Calendar.YEAR, (0 - beforeNum));
        Date dateBefore = mycalendar.getTime();
        mycalendar = Calendar.getInstance();
        mycalendar.add(Calendar.YEAR, (0 - afterNum));
        Date dateAfter = mycalendar.getTime();
        map.put("dateAfter", dateAfter);
        map.put("dateBefore", dateBefore);
        return map;
    }



    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!"".equals(dt)) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
                        "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private static java.util.Date formatDate(String split, String date, SimpleDateFormat df, int length)
            throws ParseException {

        String formatStr = "yyyy-MM-dd";
        if (length == 5) {
            // 2008-
            if (date.indexOf(split) == length - 1) {
                formatStr = "yyyy";
                date = date.substring(0, 4);
            } else {
                // 2008-01
                formatStr = "yyyy-MM";
            }
            // 2008-1 -- 2008-01
        } else if (length >= 6 && length <= 7) {
            formatStr = "yyyy-MM";
        } else if (length >= 8 && length <= 9) {
            // 2008-12-
            if (date.lastIndexOf(split) == length - 1) {
                formatStr = "yyyy-MM";
                date = date.substring(0, length - 1);
            } else {
                // 2008-1-1 -- 2008-01-01
                formatStr = "yyyy-MM-dd";
            }
        } else if (length >= 10 && length <= 11) {
            if (date.indexOf(" ") > -1 && date.indexOf(" ") < length - 1) {
                // 2008-1-1 1 --
                formatStr = "yyyy-MM-dd HH";
            } else {
                // "2008-01-01"中间无空格
                formatStr = "yyyy-MM-dd";
            }
        } else if (length >= 12 && length <= 13) {
            if (date.indexOf(":") > -1 && date.indexOf(":") < length - 1) {
                // 2008-1-1 1:1 --
                formatStr = "yyyy-MM-dd HH:mm";
                // 2008-1-1 1:01
            } else {
                // 2008-01-01 01 中间有空格
                formatStr = "yyyy-MM-dd HH";
            }
        } else if (length >= 14 && length <= 16) {
            int lastIndex = date.lastIndexOf(":");
            if (date.indexOf(":") > -1 && lastIndex < length - 1 && date.indexOf(":") != lastIndex) {
                // 2008-1-1 1:1:1
                formatStr = "yyyy-MM-dd HH:mm:ss";
                // -- 2008-01-01
                // 1:1:1 中间有两个冒号
                if (lastIndex < length - 1 - 2) {
                    date = date.substring(0, lastIndex + 3);
                }
            } else if (date.indexOf(":") > -1 && lastIndex < length - 1 && date.indexOf(":") == lastIndex) {
                // 2008-01-01 1:1 --
                formatStr = "yyyy-MM-dd HH:mm";
                // 2008-01-01
                // 01:01中间只有一个冒号
            } else if (date.indexOf(":") > -1 && lastIndex == length - 1 && date.indexOf(":") == lastIndex) {
                // 2008-01-01 01:
                formatStr = "yyyy-MM-dd HH";
                // 只有一个冒号在末尾
                date = date.substring(0, length - 1);
            }
        } else if (length == 17) {
            int lastIndex = date.lastIndexOf(":");
            if (lastIndex < length - 1) {
                // 2008-1-1 1:1:1
                formatStr = "yyyy-MM-dd HH:mm:ss";
                // -- 2008-01-01
                // 1:1:1 中间有两个冒号
                if (lastIndex < length - 1 - 2) {
                    date = date.substring(0, lastIndex + 3);
                }
            } else if (lastIndex == length - 1) {
                // 2008-01-01 1:1 --
                formatStr = "yyyy-MM-dd HH:mm";
                // 2008-01-01
                // 01:01中间只有一个冒号
                date = date.substring(0, length - 1);
            }
        } else if (length >= 18) {
            // 2008-1-1 1:1:1 --
            formatStr = "yyyy-MM-dd HH:mm:ss";
            // 2008-01-01
            // 01:01:01 有两个冒号
            int lastIndex = date.lastIndexOf(":");
            if (lastIndex < length - 1 - 2) {
                date = date.substring(0, lastIndex + 3);
            }
        }

        formatStr = formatStr.replace("-", split);
        df.applyPattern(formatStr);

        return df.parse(date);
    }

    /**
     * 将常用时间字符串转换为时间对象
     *
     * @return java.util.Date
     * @throws Exception
     */
    public static java.util.Date parseTime(String date) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat();
        java.util.Date rtnDate = null;
        if (date == null || "".equals(date.trim()) || "null".equals(date.trim())) {
            return rtnDate;
        }
        date = date.trim();
        int length = date.length();
        if (date.contains("-")) {
            if (length == 5) {
                //2008-
                if (date.indexOf("-") == length - 1) {
                    df.applyPattern("yyyy");
                    date = date.substring(0, 4);
                    rtnDate = df.parse(date);
                } else {
                    //2008-01
                    df.applyPattern("yyyy-MM");
                    rtnDate = df.parse(date);
                }
                //2008-1 -- 2008-01
            } else if (length >= 6 && length <= 7) {
                df.applyPattern("yyyy-MM");
                rtnDate = df.parse(date);
            } else if (length >= 8 && length <= 9) {
                //2008-12-
                if (date.lastIndexOf("-") == length - 1) {
                    df.applyPattern("yyyy-MM");
                    date = date.substring(0, length - 1);
                    rtnDate = df.parse(date);
                } else {
                    //2008-1-1 -- 2008-01-01
                    df.applyPattern("yyyy-MM-dd");
                    rtnDate = df.parse(date);
                }
            } else if (length >= 10 && length <= 11) {
                if (date.contains(" ") && date.indexOf(" ") < length - 1) {
                    // 2008-1-1 1 -- 2008-1-1 11 中间有空格
                    df.applyPattern("yyyy-MM-dd HH");
                    rtnDate = df.parse(date);
                } else {
                    // "2008-01-01"中间无空格
                    df.applyPattern("yyyy-MM-dd");
                    rtnDate = df.parse(date);
                }
            } else if (length >= 12 && length <= 13) {
                if (date.contains(":") && date.indexOf(":") < length - 1) {
                    //2008-1-1 1:1  -- 2008-1-1 1:01 中间有冒号
                    df.applyPattern("yyyy-MM-dd HH:mm");
                    rtnDate = df.parse(date);
                } else {
                    // 2008-01-01 01 中间有空格
                    df.applyPattern("yyyy-MM-dd HH");
                    rtnDate = df.parse(date);
                }
            } else if (length >= 14 && length <= 16) {
                int lastIndex = date.lastIndexOf(":");
                if (date.contains(":") && lastIndex < length - 1 && date.indexOf(":") != lastIndex) {
                    // 2008-1-1 1:1:1  -- 2008-01-01 1:1:1 中间有两个冒号
                    df.applyPattern("yyyy-MM-dd HH:mm:ss");
                    if (lastIndex < length - 1 - 2) {
                        date = date.substring(0, lastIndex + 3);
                    }
                    rtnDate = df.parse(date);
                } else if (date.contains(":") && lastIndex < length - 1 && date.indexOf(":") == lastIndex) {
                    // 2008-01-01 1:1  -- 2008-01-01 01:01中间只有一个冒号
                    df.applyPattern("yyyy-MM-dd HH:mm");
                    rtnDate = df.parse(date);
                } else if (date.contains(":") && lastIndex == length - 1 && date.indexOf(":") == lastIndex) {
                    // 2008-01-01 01: 只有一个冒号在末尾
                    df.applyPattern("yyyy-MM-dd HH");
                    date = date.substring(0, length - 1);
                    rtnDate = df.parse(date);
                }
            } else if (length == 17) {
                int lastIndex = date.lastIndexOf(":");
                if (lastIndex < length - 1) {
                    //2008-1-1 1:1:1  -- 2008-01-01 1:1:1 中间有两个冒号
                    df.applyPattern("yyyy-MM-dd HH:mm:ss");
                    if (lastIndex < length - 1 - 2) {
                        date = date.substring(0, lastIndex + 3);
                    }
                    rtnDate = df.parse(date);
                } else {
                    //2008-01-01 1:1  -- 2008-01-01 01:01中间只有一个冒号
                    df.applyPattern("yyyy-MM-dd HH:mm");
                    date = date.substring(0, length - 1);
                    rtnDate = df.parse(date);
                }
            } else if (length >= 18) {
                //2008-1-1 1:1:1  -- 2008-01-01 01:01:01 有两个冒号
                df.applyPattern("yyyy-MM-dd HH:mm:ss");
                int lastIndex = date.lastIndexOf(":");
                if (lastIndex < length - 1 - 2) {
                    date = date.substring(0, lastIndex + 3);
                }
                rtnDate = df.parse(date);
            }
            //对时间格式中以/为分隔符的日期做处理
        } else if (date.contains("/")) {
            rtnDate = formatDate("/", date, df, length);
        } else if (length == 4) {
            df.applyPattern("yyyy");
            rtnDate = df.parse(date);
        } else if (length >= 5 && length <= 6) {
            df.applyPattern("yyyyMM");
            rtnDate = df.parse(date);
        } else if (length >= 7 && length <= 8) {
            df.applyPattern("yyyyMMdd");
            rtnDate = df.parse(date);
        } else if (length >= 9 && length <= 10) {
            df.applyPattern("yyyyMMddHH");
            rtnDate = df.parse(date);
        } else if (length >= 11 && length <= 12) {
            df.applyPattern("yyyyMMddHHmm");
            rtnDate = df.parse(date);
        } else if (length >= 13 && length <= 14) {
            df.applyPattern("yyyyMMddHHmmss");
            rtnDate = df.parse(date);
        } else if (length >= 15) {
            df.applyPattern("yyyyMMddHHmmss");
            date = date.substring(0, 14);
            rtnDate = df.parse(date);
        }
        return rtnDate;
    }

    public static void main(String[] args) {
        Date d = new Date();
        // System.out.println(d.toString());
//		System.out.println(formatDate(d).toString());
        // System.out.println(getMonthBegin(formatDate(d).toString()));
        // System.out.println(getMonthBegin("2008/07/19"));
        // System.out.println(getMonthEnd("2008/07/19"));
        System.out.println(addDate(d, 15).toString());
    }

}
