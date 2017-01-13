package com.darren.personal.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.darren.personal.job.FileReaderJob;
import com.darren.personal.service.CustomerService;
import com.darren.personal.util.FileUtil;

@Controller
public class UploadAction {
    private static final Logger LOG = Logger.getLogger(UploadAction.class);
    @Resource
    private CustomerService customerService;

    @RequestMapping(value = "/execUpload.do")
    public String execUpload(@RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletRequest request, ModelMap model) {
        String targetPath = request.getServletContext().getRealPath("upload");
        String filePath = FileUtil.upload(file, targetPath);

        // start a new thread to read file
        FileReaderJob.startReadFileTask(customerService, filePath);

        return "iframe";
    }

    @RequestMapping(value = "/upload.do")
    public String upload() {

        return "upload";
    }
}
