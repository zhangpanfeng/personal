package com.darren.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
    private static final Logger LOG = Logger.getLogger(PropertiesUtil.class);

    public static Properties readProperties(String path) {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            // 加载属性列表
            properties.load(in);
        } catch (Exception e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.info(e.getMessage());
                    e.printStackTrace();
                }
            }

        }

        return properties;
    }
}
