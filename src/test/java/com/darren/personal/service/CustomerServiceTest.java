package com.darren.personal.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.darren.personal.entity.Customer;

//means it extends SpringJUnit4ClassRunner
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CustomerServiceTest {
    private static final Logger LOG = Logger.getLogger(CustomerServiceTest.class);
    @Resource
    private CustomerService customerService;

    @Test
    public void testInsert() {
        Customer customer = new Customer();
        customer.setPhone("18321353612");
        customer.setName("张攀峰hello");
        customer.setAge(18);
        customer.setGender("M");
        customer.setEmailState("N");
        customer.setComment("这是备注");
        // int result = customerService.insert(customer);
        // System.out.println(result);
        // System.out.println(customer.getId());
    }

    @Test
    public void testSelectAll() {
        // List<Customer> list = customerService.selectAll();
        // for(Customer customer : list){
        // System.out.println(customer);
        // }
    }

    @Test
    public void testSelectByParameter() {
        Customer p = new Customer();
        p.setName("Darren Zhang");
        // List<Customer> list = customerService.selectByParameter(p);
        // for(Customer customer : list){
        // System.out.println(customer);
        // }
    }

    @Test
    public void testUpdateByParameter() {
        Customer p = new Customer();
        p.setPhone("18321353610");
        p.setSendTime(new Date());
        p.setEmailState("Y");
        // int result = customerService.updateByParameter(p);
    }
}
