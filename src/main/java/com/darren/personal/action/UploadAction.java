package com.darren.personal.action;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.darren.personal.constant.StateCode;
import com.darren.personal.job.FileReaderJob;
import com.darren.personal.service.CustomerService;
import com.darren.personal.util.JSONResponseUtil;

@Controller
public class UploadAction {
    private static final Logger LOG = Logger.getLogger(UploadAction.class);
    @Resource
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping(value = "/execUpload.do")
    public String execUpload(@RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletRequest request, ModelMap model) {
        Map<String, Object> map = new HashedMap<String, Object>();
        map.put("result", StateCode.FAILURE);
        String path = request.getServletContext().getRealPath("upload");
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        fileName = new Date().getTime() + fileName.substring(fileName.indexOf("."));
        File targetFile = new File(path, fileName);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();

            return JSONResponseUtil.getResult(map);
        }

        // start a new thread to read file
        FileReaderJob.startReadFileTask(customerService, targetFile.getAbsolutePath());

        map.put("result", StateCode.SUCCESS);

        return JSONResponseUtil.getResult(map);
    }

    @RequestMapping(value = "/upload.do")
    public String upload() {

        return "upload";
    }
}
