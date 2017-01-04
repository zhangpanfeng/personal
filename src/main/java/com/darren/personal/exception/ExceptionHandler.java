package com.darren.personal.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.darren.personal.util.LogUtil;

public class ExceptionHandler implements HandlerExceptionResolver {
    private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse rep, Object obj, Exception e) {
        LogUtil.error(LOG, e);
        ModelAndView modelView = new ModelAndView("redirect:/error.jsp");

        return modelView;
    }
}