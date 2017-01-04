package com.darren.personal.task;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.darren.personal.service.BaseService;
import com.darren.personal.util.ExcelFileReaderUtil;

public class FileReaderTask implements Runnable {
    private static final Logger LOG = Logger.getLogger(FileReaderTask.class);
    private BaseService baseService;
    private String filePath;
    private boolean isOverwrite;

    public FileReaderTask(BaseService baseService, String filePath, boolean isOverwrite) {
        this.baseService = baseService;
        this.filePath = filePath;
        this.isOverwrite = isOverwrite;
    }

    @Override
    public void run() {
        try {
            Map<String, List<String>> result = ExcelFileReaderUtil.readExcel(filePath);
            Set<String> keySet = result.keySet();
            for (String key : keySet) {
                List<String> contentList = result.get(key);
                if (isOverwrite) {
                    baseService.updateFromFile(contentList);
                } else {
                    baseService.insertFromFile(contentList);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
