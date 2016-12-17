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
    private static final String SCRIPT_PATH = "shellScript";

    @RequestMapping(value = "/build.do")
    public String build() {

        return "build";
    }

    @RequestMapping(value = "/execShell.do")
    public void execShell(HttpServletRequest request) {
        String shellScript = request.getServletContext().getInitParameter(SCRIPT_PATH);
        
        Process process = null;
        try {
            File scriptFile = ResourceUtils.getFile(shellScript);  
            String scriptPath = scriptFile.getAbsolutePath();
            String command1 = "chmod 777 " + scriptPath;
            String command2 = "sh " + scriptPath;
            BufferedReader re = new BufferedReader( new InputStreamReader(new FileInputStream(scriptFile)));
            String li = null;
            while ((li = re.readLine()) != null){  
                LOG.info(li);
            } 
            Runtime run = Runtime.getRuntime();
            process = run.exec(command1);
            
            if(process.waitFor() == 0){
                process = run.exec(command2);
                BufferedReader reader = new BufferedReader( new InputStreamReader(process.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null){  
                    LOG.info(line);
                } 
                int result = process.waitFor();
                LOG.info("result = " + result);
                reader.close();
            }else{
                LOG.info("Set permission failed");
            }
            re.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
    }
}
