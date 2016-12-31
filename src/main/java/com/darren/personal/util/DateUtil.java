package com.darren.personal.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtil {
    private static final Logger LOG = Logger.getLogger(DateUtil.class);
    private static final String DEFAULT_DATA_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * get string use default format "yyyy-MM-dd HH:mm"
     * 
     * @param date
     * @return String "yyyy-MM-dd HH:mm"
     */
    public static String getString(Date date) {
        return getString(DEFAULT_DATA_FORMAT, date);
    }

    /**
     * get string use format
     * 
     * @param format
     *            like "yyyy-MM-dd HH:mm"
     * @param date
     * @return String like "yyyy-MM-dd HH:mm"
     */
    public static String getString(String format, Date date) {
        DateFormat formater = new SimpleDateFormat(format);
        String result = formater.format(date);

        return result;
    }

    /**
     * get date use default format "yyyy-MM-dd HH:mm"
     * 
     * @param value
     *            like "2016-12-30 15:15"
     * @return Date
     */
    public static Date getDate(String value) {
        return getDate(DEFAULT_DATA_FORMAT, value);
    }

    /**
     * get date use default format like "yyyy-MM-dd HH:mm"
     * 
     * @param format
     *            like "yyyy-MM-dd HH:mm"
     * @param value
     *            like "2016-12-30 15:15"
     * @return date
     */
    public static Date getDate(String format, String value) {
        DateFormat formater = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formater.parse(value);
        } catch (ParseException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return date;
    }

    public static void main(String[] args) {
        System.out.println(getDate("2016-12-12 11:10"));
    }
}
