package com.darren.personal.util;

public class StringUtil {

    public static boolean isEmpty(String source) {
        if (source == null || "".equals(source)) {
            return true;
        } else {
            return false;
        }
    }
}
