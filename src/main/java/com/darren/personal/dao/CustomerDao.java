package com.darren.personal.dao;

import java.util.List;

import com.darren.personal.entity.Customer;

public interface CustomerDao {
    /**
     * insert customer to table
     * 
     * @param customer
     * @return
     */
    public String insert(Customer customer);

    /**
     * update customer by parameter
     * 
     * @param customer
     * @return
     */
    public String updateByParameter(Customer customer);

    /**
     * delete customer by id
     * 
     * @param customer
     * @return
     */
    public String deleteById(Customer customer);

    /**
     * select customer list by parameter
     * 
     * @param customer
     * @return
     */
    public List<Customer> selectByParameter(Customer customer);

    /**
     * select all customer
     * 
     * @return
     */
    public List<Customer> selectAll();
}
