package com.darren.personal.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.darren.personal.entity.Customer;

public class EmailScheduleJob {
    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final Map<String, EmailTask> EMAIL_TASK_MAP = new HashMap<String, EmailTask>();

    public static void scheduleTask(List<Customer> customerList, String configPath, String taskId, Date sendTime) {
        EmailTask task = EMAIL_TASK_MAP.get(taskId);
        if (task == null) {
            schedule(customerList, configPath, taskId, sendTime);
            
        } else {
            Future<?> future = task.getFuture();
            future.cancel(true);
            schedule(customerList, configPath, taskId, sendTime);
        }
    }
    
    private static void schedule(List<Customer> customerList, String configPath, String taskId, Date sendTime){
        long delayTime = sendTime.getTime() - new Date().getTime();
        if(delayTime > 0){
            EmailTask task = new EmailTask(customerList, configPath, taskId);
            // if send email failed, send it again after 5 minutes
            Future<?> future = SERVICE.scheduleAtFixedRate(task, delayTime, 5 * 60 * 1000, TimeUnit.MILLISECONDS);
            task.setFuture(future);
            EMAIL_TASK_MAP.put(taskId, task);
        }
    }

    public static void removeTask(String taskId) {
        if (EMAIL_TASK_MAP.get(taskId) != null) {
            EMAIL_TASK_MAP.remove(taskId);
        }
    }
}
