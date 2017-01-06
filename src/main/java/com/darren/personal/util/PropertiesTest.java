package com.darren.personal.util;

import java.io.IOException;

public class PropertiesTest {
    private static String FILE_KEY = "test.output.properties";

    private static void setUp() {
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\test.properties";
        System.setProperty(FILE_KEY, filePath);
    }



    public static void main(String[] args) throws IOException {
        setUp();
        //PropertiesUtil.save(System.getProperty(FILE_KEY), "hello", "world");
        //PropertiesUtil.save(System.getProperty(FILE_KEY), "hello1", "world");
        //PropertiesUtil.save(System.getProperty(FILE_KEY), "hello2", "world");
        
        PropertiesUtil.delete(System.getProperty(FILE_KEY), "hello1");
    }

}
