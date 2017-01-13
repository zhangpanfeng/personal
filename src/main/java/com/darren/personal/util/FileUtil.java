package com.darren.personal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    private static final Logger LOG = Logger.getLogger(FileUtil.class);

    /**
     * upload file
     * 
     * @param file
     * @param targetPath
     * @return
     */
    public static String upload(MultipartFile file, String targetPath) {
        createFolder(targetPath);

        String fileName = file.getOriginalFilename();
        fileName = new Date().getTime() + fileName.substring(fileName.indexOf("."));
        File targetFile = new File(targetPath, fileName);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();

            return null;
        }

        return targetFile.getAbsolutePath();
    }

    /**
     * download file
     * 
     * @param filePath
     * @param response
     * @return
     */
    public static boolean download(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        FileInputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            // auto check the file type
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();

            return false;
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * create a folder base on the path
     * 
     * @param path
     */
    public static void createFolder(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
