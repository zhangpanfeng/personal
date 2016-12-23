package com.darren.personal.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.darren.personal.constant.StateCode;
import com.darren.personal.entity.User;
import com.darren.personal.websocket.TextWebSocketServer;

@Controller
public class BuildAction {
    private static final Logger LOG = Logger.getLogger(BuildAction.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String SCRIPT_PATH = "shellScript";

    @RequestMapping(value = "/build.do")
    public String build(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "build";
    }

    @ResponseBody
    @RequestMapping(value = "/execShell.do")
    public String execShell(HttpServletRequest request, String token) {
        String result = null;
        Map<String, Object> map = new HashMap<String, Object>();
        TextWebSocketServer webSocketServer = TextWebSocketServer.getSocketServer(token);
        if (webSocketServer == null) {
            map.put("result", StateCode.FAILURE);
            try {
                result = MAPPER.writeValueAsString(map);
            } catch (IOException e) {
                LOG.info(e.getMessage());
                e.printStackTrace();
            }

            return result;
        }
        String shellScript = request.getServletContext().getInitParameter(SCRIPT_PATH);

        Process process = null;
        try {
            File scriptFile = ResourceUtils.getFile(shellScript);
            String scriptPath = scriptFile.getAbsolutePath();
            String command1 = "chmod 777 " + scriptPath;
            String command2 = "sh " + scriptPath;
            BufferedReader re = new BufferedReader(new InputStreamReader(new FileInputStream(scriptFile)));
            String li = null;
            while ((li = re.readLine()) != null) {
                LOG.info(li);
            }
            Runtime run = Runtime.getRuntime();
            process = run.exec(command1);

            if (process.waitFor() == 0) {
                process = run.exec(command2);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    webSocketServer.onMessage(line);
                }
                LOG.info("result = " + process.waitFor());
                reader.close();
            } else {
                LOG.info("Set permission failed");
            }
            re.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        webSocketServer.onClose();
        map.put("result", StateCode.SUCCESS);
        try {
            result = MAPPER.writeValueAsString(map);
        } catch (IOException e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
