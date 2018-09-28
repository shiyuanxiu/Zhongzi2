package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private DateUtil(){}

    public static final String YYYYMMDD = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 获取时间戳，单位ms
     */
    public static long getTimeStamp() {
        return new Date().getTime();
    }

    /**
     * 获取格式化的时间
     */
    public static String getFormatDate(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(time);
    }

    /**
     * 将时间戳转化为标准时间
     */
    public static Date timestampToDate(long time){
        Date date = new Date(time);
        return date;
    }

    /**
     * Date转 UTC String
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    /**
     *  String UTC时间转Date
     * @param str
     * @return
     */
    public static Date string2Date(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;

        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(getTimeStamp());
        System.out.println(getFormatDate(getTimeStamp()));
        System.out.println(timestampToDate(getTimeStamp()));
        Date date = new Date();
        String UTCStr = date2String(date);
        System.out.println(UTCStr);
        System.out.println(string2Date(UTCStr));

    }
}
