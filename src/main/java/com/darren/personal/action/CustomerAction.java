package com.darren.personal.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.darren.personal.entity.Customer;
import com.darren.personal.service.CustomerService;

@Controller
public class CustomerAction {
    @Resource
    private CustomerService customerService;

    @RequestMapping(value = "/anne.do")
    public String anne(ModelMap model, HttpServletRequest request, Customer customer) {
        List<Customer> customerList = null;
        if(customer == null){
            customerList = customerService.selectAll();
        }else{
            customerList = customerService.selectByParameter(customer);
        }
        model.put("customerList", customerList);

        return "anne";
    }
}
