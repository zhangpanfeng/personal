package com.darren.personal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String DEFAULT_DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * get string date use default format "yyyy-MM-dd HH:mm:ss"
     * 
     * @param date
     * @return String "yyyy-MM-dd HH:mm:ss"
     */
    public static String getString(Date date) {
        return getString(DEFAULT_DATA_FORMAT, date);
    }

    /**
     * get string date use format
     * 
     * @param format
     *            "yyyy-MM-dd HH:mm:ss"
     * @param date
     * @return String "yyyy-MM-dd HH:mm:ss"
     */
    public static String getString(String format, Date date) {
        DateFormat formater = new SimpleDateFormat(format);
        String result = formater.format(date);

        return result;
    }
}
