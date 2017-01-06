package com.darren.personal.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
    private static final Logger LOG = Logger.getLogger(PropertiesUtil.class);

    public static Properties readProperties(String path) {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            // Load attribute list
            properties.load(in);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                    e.printStackTrace();
                }
            }

        }

        return properties;
    }

    public static Properties readProperties(File file) {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            // Load attribute list
            properties.load(in);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

    /**
     * save [key, value] to target file
     * 
     * @param path
     *            file path
     * @param key
     * @param value
     */
    public static void save(String path, String key, String value) {
        Properties properties = readProperties(path);
        properties.setProperty(key, value);
        try {
            OutputStream output = new FileOutputStream(path);
            properties.store(output, "Update '" + key + "' value");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * delete key from properties file
     * 
     * @param path
     *            file path
     * @param key
     */
    public static void delete(String path, String key) {
        Properties properties = readProperties(path);
        properties.remove(key);
        try {
            OutputStream output = new FileOutputStream(path);
            properties.store(output, "Delete '" + key + "' value");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
