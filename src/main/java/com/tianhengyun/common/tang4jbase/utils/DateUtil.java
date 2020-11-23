package com.tianhengyun.common.tang4jbase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    /**
     * utc时间转成local时间
     * @param utcTime 国际时间
     * @return 北京时间
     */
    public static Date utcToLocal(String utcTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date localDate = null;
        String localTime = sdf.format(utcDate != null ? utcDate.getTime() : 0);
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;
    }

    /**
     * utc时间转成local时间
     * @param utcDate 国际时间
     * @return 北京时间
     */
    public static Date utcToLocal(Date utcDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.setTimeZone(TimeZone.getDefault());
        Date localDate = null;
        String localTime = sdf.format(utcDate != null ? utcDate.getTime() : 0);
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDate;
    }





}
