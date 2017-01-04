package com.darren.personal.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.darren.personal.entity.Customer;

public class TaskTest {
    private static final String BASE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\";
    public static void main(String[] args) {  
        Customer customer = new Customer();
        customer.setPhone("18321353610");
        customer.setName("张攀峰");
        customer.setComment("这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注这个是备注");
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(customer);
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
//        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);  
        String configPath = BASE_PATH + "mail.properties";
        EmailTask task = new EmailTask(customerList, configPath, "111");
       Future<?> future = service.scheduleAtFixedRate(task,2, 2, TimeUnit.SECONDS);
       task.setFuture(future);
//        service.schedule(runnable, 1, TimeUnit.SECONDS);
//        service.schedule(runnable, 1, TimeUnit.SECONDS);
       
       new Date().getTime();
    }  
}
