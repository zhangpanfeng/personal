package com.darren.personal.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.darren.personal.entity.Customer;

public class TaskTest {
    public static void main(String[] args) {  
        List<Customer> customerList = new ArrayList<Customer>();
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
//        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);  
        EmailTask task = new EmailTask(customerList);
       Future<?> future = service.scheduleAtFixedRate(task,2, 2, TimeUnit.SECONDS);
       task.setFuture(future);
//        service.schedule(runnable, 1, TimeUnit.SECONDS);
//        service.schedule(runnable, 1, TimeUnit.SECONDS);
    }  
}
