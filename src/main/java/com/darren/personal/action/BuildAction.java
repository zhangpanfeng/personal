package com.darren.personal.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BuildAction {
    private static final Logger LOG = Logger.getLogger(BuildAction.class);
    private static final String SCRIPT_PATH = "scriptPath";

    @RequestMapping(value = "/build.do")
    public String build() {
        LOG.info("go to build page");
        return "build";
    }

    @RequestMapping(value = "/execShell.do")
    public void execShell(HttpServletRequest request) {
        String shellScript = request.getServletContext().getInitParameter(SCRIPT_PATH);
        
        Process process = null;
        try {
            File scriptFile = ResourceUtils.getFile(shellScript);  
            String scriptPath = scriptFile.getAbsolutePath();
            LOG.info("scriptPath = " + scriptPath);
            String command = "chmod 777 " + scriptPath;
            BufferedReader re = new BufferedReader( new InputStreamReader(new FileInputStream(scriptFile)));
            String li = null;
            while ((li = re.readLine()) != null){  
                LOG.info(li);
            } 
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader( new InputStreamReader(process.getInputStream()));
            int result = process.waitFor();
            LOG.info("result = " + result);
            String line = null;
            while ((line = reader.readLine()) != null){  
                LOG.info(line);
            } 
            reader.close();
            re.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
    }
}
