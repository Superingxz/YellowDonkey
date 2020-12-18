package com.ruanjie.donkey.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/12. 时间数据转化帮助类
 */
public class TimeUtils {

    // SimpleDateFormat 类的格式化字符：
    // G 年代指示符(AD)
    // y 年(yy:10 y/yyy/yyyy:2010)
    // M 月(M:1 MM:01 MMM:Jan MMMM:January MMMMM:J)
    // L 独立月(L:1 LL:01 LLL:Jan LLLL:January LLLLL:J)
    // d 一个月中的第几日(只需此一个字符，输出如：10)
    // h 时(只需此一个字符，输出如：上/下午 1 ~ 12)
    // H 时(只需此一个字符，输出如：0 ~ 23)
    // k 一天中的第几个小时(只需此一个字符，输出如：1 ~ 24)
    // K 时(上/下午 0 ~ 11)
    // m 一小时中的第几分(只需此一个字符，输出如：30)
    // s 一分钟中的第几秒(只需此一个字符，输出如：55)
    // S 毫秒(只需此一个字符，输出如：978)
    // E 星期几(E/EE/EEE:Tue, EEEE:Tuesday, EEEEE:T)
    // c 独立星期几(c/cc/ccc:Tue, cccc:Tuesday, ccccc:T)
    // D 一年中的第几天(只需此一个字符，输出如：189)
    // F 一月中的第几个星期几(只需此一个字符，输出如：2)
    // w 一年中的第几个星期(只需此一个字符，输出如：27)
    // W 一月中的第几个星期(只需此一个字符，输出如：1)
    // a 上/下午标记符(只需此一个字符，输出如：AM/PM)
    // Z 时区(RFC822)(Z/ZZ/ZZZ:-0800 ZZZZ:GMT-08:00 ZZZZZ:-08:00)
    // z 时区(z/zz/zzz:PST zzzz:Pacific Standard Time)
    // 要忽略的字符都要用单引号('')括住！
    //  eg:  SimpleDateFormat sdf = new SimpleDateFormat("'Date'yyyy-MM-dd'Time'HH:mm:ss'Z'");

    //   type: "yyyy年MM月dd日" "yyyy-MM-dd HH:mm:ss" "yyyy年MM月dd日 HH:00" "yyyy-MM-dd HH:mm:ss"

    /**
     * 将字符串转为时间戳
     *
     * @param str
     * @param type
     * @return
     */
    public static long str2Time(String str, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date date = new Date();
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String date2Str(Date date, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    /**
     * 将时间戳(毫秒13位数)转为字符串
     *
     * @param stamp
     * @param type
     * @return
     */
    public static String time2Str(long stamp, String type) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        str = sdf.format(new Date(stamp));
        return str;
    }

    public static String timeStr2Str(String stamp, String type) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        str = sdf.format(new Date(Long.parseLong(stamp)));
        return str;
    }

    /**
     * 保留两位小数
     *
     * @param v1
     */
    public static Double keepTwo(Double v1) {

        BigDecimal bd = new BigDecimal(v1.toString());
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);//2位小数

        return bd.doubleValue();
    }

    /**
     * 保留多位小数
     *
     * @param number1
     * @param number2
     * @param digit
     * @return
     */
    public static float keepDigit(int number1, int number2, int digit) {
        int number = number1 + number2;
        String tempStr = handleDigit(digit);
        int tempInt = handleDigitF(digit);
        DecimalFormat fnum = new DecimalFormat(tempStr);
        String dd = fnum.format(number * 1.0f / tempInt);
//        L.i("keepDigit", "dd = " + dd);
        //1.将数据除以digit位数
        return Float.parseFloat(dd);
    }

    public static float keepDigit1(int number, int digit) {
        String tempStr = handleDigit(digit);
        int tempInt = handleDigitF(digit);
        DecimalFormat fnum = new DecimalFormat(tempStr);
        String dd = fnum.format(number * 1.0f);
//        L.i("keepDigit", "dd = " + dd);
        //1.将数据除以digit位数
        return Float.parseFloat(dd);
    }

    private static String handleDigit(int digit) {
        String temp = "###.";
        for (int i = 0; i < digit; ++i) {
            temp += "0";
        }
        return temp;
    }

    private static int handleDigitF(int digit) {
        String temp = "1";
        for (int i = 0; i < digit; ++i) {
            temp += "0";
        }
        return Integer.parseInt(temp);
    }

    /**
     * 时间格式化 将 秒 转换成 00:00:00 格式显示
     */
    public static String formattedTime(long second) {
        String hs, ms, ss, formatTime;

        long h, m, s;
        h = second / 3600;
        m = (second % 3600) / 60;
        s = (second % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
//        if (hs.equals("00")) {
//            formatTime = ms + ":" + ss;
//        } else {
        formatTime = hs + ":" + ms + ":" + ss;
//        }

        return formatTime;
    }
    /**
     * 时间格式化 将 秒 转换成 00:00 格式显示
     */
    public static String formatTimer(long second) {
        String ms, ss, formatTime;

        long  m, s;
        m = (second % 3600) / 60;
        s = (second % 3600) % 60;

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
//        if (hs.equals("00")) {
//            formatTime = ms + ":" + ss;
//        } else {
        formatTime =  ms + ":" + ss;
//        }

        return formatTime;
    }


    /**
     * 秒为单位
     *
     * @param seconds
     * @return
     */
    public static String getStandardDate(long seconds) {
        String temp = "";
        try {
            long now = System.currentTimeMillis() / 1000; //转换成秒

            long differ = now - seconds;
            long months = differ / (60 * 60 * 24 * 30);
            long days = differ / (60 * 60 * 24);
            long hours = differ / (60 * 60);
            long minutes = differ / 60;
            if (months > 0) {
                temp = months + "月前";
            } else if (days > 0) {
                temp = days + "天前";
            } else if (hours > 0) {
                temp = hours + "小时前";
            } else if (minutes > 0) {
                temp = minutes + "分钟前";
            } else {
                temp = "1分钟前";
//                temp = seconds + "秒前";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 将多少秒换算成多少小时多少分
     *
     * @param str
     * @return
     */
    public static String caculTime(String str) {
        int second = Integer.parseInt(str);
        int hour = second / 3600;
        int min = second % 3600 / 60;
        String temp = "";
        if (hour == 0) {
            if (min == 0) {
                return "0";
            } else {
                return min + "分";
            }
        } else {
            return hour + "时" + min + "分";
        }
    }

    public static String mAdd(String v1, String v2, int mScale) {
        BigDecimal bd1 = new BigDecimal(v1);
        BigDecimal bd2 = new BigDecimal(v2);

        BigDecimal bd3 = bd1.add(bd2);
        bd3 = bd3.setScale(mScale, BigDecimal.ROUND_HALF_UP);//2位小数

        return bd3+"";
    }

    public static String mSub(String v1, String v2, int mScale) {

        BigDecimal bd1 = new BigDecimal(v1);
        BigDecimal bd2 = new BigDecimal(v2);

        BigDecimal bd3 = bd1.subtract(bd2);

        bd3 = bd3.setScale(mScale, BigDecimal.ROUND_HALF_UP);
        return bd3 + "";
    }

    public static String mMul(String v1, String v2, int mScale) {
        BigDecimal bd1 = new BigDecimal(v1);
        BigDecimal bd2 = new BigDecimal(v2);

        BigDecimal bd3 = bd1.multiply(bd2);
        bd3 = bd3.setScale(mScale, BigDecimal.ROUND_HALF_UP);

        return bd3+"";
    }

    public static String mDiv(String v1, String v2, int mScale) {
        if("0".equals(v2)){
            return "0";
        }
        BigDecimal bd1 = new BigDecimal(v1);
        BigDecimal bd2 = new BigDecimal(v2);

        BigDecimal bd3 = bd1.divide(bd2, BigDecimal.ROUND_HALF_UP);
        bd3 = bd3.setScale(mScale, BigDecimal.ROUND_HALF_UP);

        return bd3+"";
    }
}