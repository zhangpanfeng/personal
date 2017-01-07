package com.darren.personal.util;

import org.springframework.context.ApplicationContext;

public class SpringContextUtil {
    private static ApplicationContext applicationContext;

    /**
     * save applicationContext to static variable
     * 
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;

    }

    /**
     * get bean by name
     * 
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * get bean by class
     * 
     * @param classz
     * @return
     */
    public static Object getBean(Class<?> classz) {
        return applicationContext.getBean(classz);
    }
}
