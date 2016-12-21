package com.darren.personal.util;

import org.apache.log4j.Logger;

public class LogUtil {

    /**
     * Print log info for exception / LOG Level INFO
     * 
     * @param logger
     * @param t
     */
    public static void info(Logger logger, Throwable t) {
        StackTraceElement[] stackTraceArray = t.getStackTrace();
        for (StackTraceElement stackTrace : stackTraceArray) {
            logger.info(stackTrace.toString());
        }
    }

    /**
     * Print log error for exception / LOG Level ERROR
     * 
     * @param logger
     * @param t
     */
    public static void error(Logger logger, Throwable t) {
        StackTraceElement[] stackTraceArray = t.getStackTrace();
        for (StackTraceElement stackTrace : stackTraceArray) {
            logger.error(stackTrace.toString());
        }
    }
}
