package com.darren.personal.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
    public int insert(Customer customer) {
        int result = customerDao.insert(customer);

        return result;
    }

    @Override
    public int updateByParameter(Customer customer) {
        int result = customerDao.updateByParameter(customer);

        return result;
    }

    @Override
    public void updateScheduledCustomer(String stringId) {
        String[] array = stringId.split(",");
        for (String sid : array) {
            int id = Integer.parseInt(sid);
            Customer customer = customerDao.selectByPrimaryKey(id);
            customer.setEmailState(Customer.EmailState.Y.toString());
            customerDao.updateByParameter(customer);
        }
    }

    @Override
    public int deleteById(Customer customer) {
        customer.setDeleteTime(new Date());
        int result = customerDao.deleteById(customer);

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

    @Override
    public Customer selectByPrimaryKey(int id) {
        Customer customer = customerDao.selectByPrimaryKey(id);

        return customer;
    }

    @Override
    public void insertFromFile(List<String> contentList) {
        if (contentList != null) {
            for (String line : contentList) {
                String[] array = line.split("\\|");
                String phone = String.valueOf((long) Double.parseDouble(array[0]));
                if (phone.length() > 11) {
                    continue;
                }

                Customer customer = new Customer();
                customer.setPhone(phone);
                List<Customer> list = customerDao.selectByParameter(customer);
                if (list == null || list.size() == 0) {
                    customer.setName(array[1]);
                    customer.setComment(array[2]);
                    customerDao.insert(customer);
                }
            }
        }
    }

    @Override
    public void updateFromFile(List<String> contentList) {
        if (contentList != null) {
            for (String line : contentList) {
                String[] array = line.split("\\|");
                String phone = String.valueOf((long) Double.parseDouble(array[0]));
                if (phone.length() > 11) {
                    continue;
                }

                Customer customer = new Customer();
                customer.setPhone(phone);
                List<Customer> list = customerDao.selectByParameter(customer);
                if (list == null || list.size() == 0) {
                    customer.setName(array[1]);
                    customer.setComment(array[2]);
                    customerDao.insert(customer);
                } else {
                    customer = list.get(0);
                    customer.setName(array[1]);
                    customer.setComment(array[2]);
                    customerDao.updateByParameter(customer);
                }
            }
        }
    }
}
