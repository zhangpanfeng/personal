package com.darren.personal.util;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;


public class JSONResponseUtil {
    private static final Logger LOG = Logger.getLogger(JSONResponseUtil.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public static String getResult(Map<String, Object> parameter){
        String result = null;
        try {
            result = MAPPER.writeValueAsString(parameter);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
