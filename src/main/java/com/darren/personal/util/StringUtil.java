package com.darren.personal.util;

public class StringUtil {

    /**
     * check if the source is empty
     * 
     * @param source
     * @return True: null or "", False otherwise
     */
    public static boolean isEmpty(String source) {
        if (source == null || "".equals(source)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * replace the part of the whole string and return
     * 
     * @param wholeString
     *            whole string
     * @param replaceString
     *            need to be replaced
     * @return String which has been replaced
     */
    public static String getHighLightString(String wholeString, String replaceString) {
        String target = wholeString.replaceAll(replaceString, "<font color='red'>" + replaceString + "</font>");

        return target;
    }
}
