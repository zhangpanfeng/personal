package com.darren.personal.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.darren.personal.dao.CustomerDao;
import com.darren.personal.entity.Customer;
import com.darren.personal.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerDao customerDao;

    @Override
    public String insert(Customer customer) {
        String result = customerDao.insert(customer);

        return result;
    }

    @Override
    public String updateByParameter(Customer customer) {
        String result = customerDao.updateByParameter(customer);

        return result;
    }

    @Override
    public String deleteById(Customer customer) {
        String result = customerDao.deleteById(customer);

        return result;
    }

    @Override
    public List<Customer> selectByParameter(Customer customer) {
        List<Customer> result = new ArrayList<Customer>();
        List<Customer> customerList = customerDao.selectByParameter(customer);
        if (customerList != null) {
            result.addAll(customerList);
        }

        return result;
    }

    @Override
    public List<Customer> selectAll() {
        List<Customer> result = new ArrayList<Customer>();
        List<Customer> customerList = customerDao.selectAll();
        if (customerList != null) {
            result.addAll(customerList);
        }

        return result;
    }

}
