package com.darren.personal.job;

import com.darren.personal.service.BaseService;
import com.darren.personal.task.FileReaderTask;

public class FileReaderJob {

    /**
     * start a new thread to read file to database, jump the same phone record
     * 
     * @param baseService
     * @param filePath
     */
    public static void startReadFileTask(BaseService baseService, String filePath) {
        FileReaderTask task = new FileReaderTask(baseService, filePath, false);
        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * start a new thread to read file to database
     * 
     * @param baseService
     * @param filePath
     * @param isOverwrite
     *            True: overwrite the same phone record, False: jump the same phone record
     */
    public static void startReadFileTask(BaseService baseService, String filePath, boolean isOverwrite) {
        FileReaderTask task = new FileReaderTask(baseService, filePath, isOverwrite);
        Thread thread = new Thread(task);
        thread.start();
    }
}
