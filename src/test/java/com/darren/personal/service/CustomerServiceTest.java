package com.darren.personal.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.darren.personal.entity.Customer;

@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CustomerServiceTest {
    private static final Logger LOG = Logger.getLogger(CustomerServiceTest.class);
    @Resource  
    private CustomerService customerService;
    
    @Test
    public void testInsert() {
        Customer customer = new Customer();
        customer.setPhone("18321353610");
        customer.setName("Darren Zhang");
        customer.setAge(18);
        customer.setGender("M");
        customer.setCreateTime(new Date());
        String result = customerService.insert(customer);
        System.out.println(result);
    }
}
