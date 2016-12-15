package com.darren.personal.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BuildAction {
    private static final Logger LOG = Logger.getLogger(BuildAction.class);
    private static final String BASE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "main" + File.separator + "resources" + File.separator + "build.sh";

    @RequestMapping(value = "/build.do")
    public String build() {
        LOG.info("go to build page");
        return "build";
    }

    @RequestMapping(value = "/execShell.do")
    public void execShell() {
        String command = "chmod 777 " + BASE_PATH;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader( new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null){  
                LOG.info(line);
            } 
            int result = process.waitFor();
            LOG.info("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
    }
}
