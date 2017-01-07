package com.darren.personal.job;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.darren.personal.task.EmailTask;

public class EmailScheduleJob {
    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final Map<String, EmailTask> EMAIL_TASK_MAP = new HashMap<String, EmailTask>();

    /**
     * schedule email task
     * @param emailContent email text
     * @param configPath email configure path
     * @param taskId task id
     * @param localSendTime local time to send email
     */
    public static void scheduleTask(String emailContent, String configPath, String taskId, long localSendTime) {
        EmailTask task = EMAIL_TASK_MAP.get(taskId);
        if (task == null) {
            schedule(emailContent, configPath, taskId, localSendTime);

        } else {
            Future<?> future = task.getFuture();
            future.cancel(true);
            schedule(emailContent, configPath, taskId, localSendTime);
        }
    }

    private static void schedule(String emailContent, String configPath, String taskId, long localSendTime) {
        long delayTime = localSendTime - new Date().getTime();
        if (delayTime > 0) {
            EmailTask task = new EmailTask(emailContent, configPath, taskId);
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
