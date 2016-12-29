package com.darren.personal.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.darren.personal.entity.Customer;
import com.darren.personal.service.CustomerService;

@Controller
public class CustomerAction {
    private static final Logger LOG = Logger.getLogger(CustomerAction.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
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
    
    @ResponseBody
    @RequestMapping(value = "/getCustomer.do")
    public Object getCustomer(Customer customer){
        String result = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Customer> customerList= customerService.selectByParameter(customer);
        if(customerList != null && customerList.size() > 0){
            customer = customerList.get(0);
            map.put("phone", customer.getPhone());
            map.put("name", customer.getName());
            map.put("result", 1);
        }else{
            map.put("result", 0);
        }
        
        try {
            result = MAPPER.writeValueAsString(map);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
}
