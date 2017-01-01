package com.darren.personal.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.darren.personal.constant.Constant;
import com.darren.personal.constant.StateCode;
import com.darren.personal.entity.Customer;
import com.darren.personal.job.EmailScheduleJob;
import com.darren.personal.service.CustomerService;
import com.darren.personal.util.JSONResponseUtil;
import com.darren.personal.util.StringUtil;

@Controller
public class CustomerAction {
    private static final Logger LOG = Logger.getLogger(CustomerAction.class);

    @Resource
    private CustomerService customerService;

    @RequestMapping(value = "/anne.do")
    public String anne(ModelMap model, HttpServletRequest request, Customer customer) {
        List<Customer> customerList = customerService.selectByParameter(customer);
        if (customerList != null
                && (StringUtil.isEmpty(customer.getPhone()) || StringUtil.isEmpty(customer.getName()))) {
            for (Customer item : customerList) {
                if (!StringUtil.isEmpty(customer.getPhone())) {
                    item.setPhone(item.getPhone().replaceAll(customer.getPhone(),
                            "<font color='red'>" + customer.getPhone() + "</font>"));
                }
                if (!StringUtil.isEmpty(customer.getName())) {
                    item.setName(item.getName().replaceAll(customer.getName(),
                            "<font color='red'>" + customer.getName() + "</font>"));
                }
            }
        }

        model.put("customerList", customerList);

        return "anne";
    }

    @ResponseBody
    @RequestMapping(value = "/getCustomer.do")
    public String getCustomer(Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", 0);
        customer = customerService.selectByPrimaryKey(customer.getId());
        if (customer != null) {
            map.put("id", customer.getId());
            map.put("phone", customer.getPhone());
            map.put("name", customer.getName());
            map.put("comment", customer.getComment());
            map.put("result", 1);
        }

        return JSONResponseUtil.getResult(map);
    }

    @ResponseBody
    @RequestMapping(value = "/editCustomer.do")
    public String editCustomer(HttpServletRequest request, Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", 0);
        int rowCount = customerService.updateByParameter(customer);
        if (rowCount > 0) {
            Date sendTime = customer.getSendTime();
            customer = customerService.selectByPrimaryKey(customer.getId());
            if (customer != null) {
                // schedule email task
                if (sendTime != null) {
                    List<Customer> customerList = new ArrayList<Customer>();
                    customerList.add(customer);
                    String configPath = (String) request.getServletContext().getAttribute(Constant.EMAIL_CONFIG_PATH);
                    EmailScheduleJob.scheduleTask(customerList, configPath, String.valueOf(customer.getId()), sendTime);
                }
                map.put("id", customer.getId());
                map.put("phone", customer.getPhone());
                map.put("name", customer.getName());
                map.put("emailState", customer.getEmailState());
                map.put("stringSendTime", customer.getStringSendTime());
                map.put("comment", customer.getComment());
                map.put("result", 1);
            }
        }

        return JSONResponseUtil.getResult(map);
    }

    @ResponseBody
    @RequestMapping(value = "/addCustomer.do")
    public String addCustomer(Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", 0);
        int rowCount = customerService.insert(customer);
        if (rowCount > 0) {
            customer = customerService.selectByPrimaryKey(customer.getId());
            if (customer != null) {
                map.put("id", customer.getId());
                map.put("phone", customer.getPhone());
                map.put("name", customer.getName());
                map.put("emailState", customer.getEmailState());
                map.put("stringSendTime", customer.getStringSendTime());
                map.put("comment", customer.getComment());
                map.put("result", 1);
            }
        }

        return JSONResponseUtil.getResult(map);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteCustomer.do")
    public String deleteCustomer(Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        int rowCount = customerService.deleteById(customer);
        map.put("result", rowCount);

        return JSONResponseUtil.getResult(map);
    }

    @ResponseBody
    @RequestMapping(value = "/batchSchedule.do")
    public String batchSchedule(HttpServletRequest request, Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", StateCode.FAILURE);
        List<Customer> customerList = new ArrayList<Customer>();
        if (customer != null && !StringUtil.isEmpty(customer.getStringId())) {
            String[] idArray = customer.getStringId().split(",");
            for (String stringId : idArray) {
                Customer item = customerService.selectByPrimaryKey(Integer.valueOf(stringId.trim()));
                item.setSendTime(customer.getSendTime());
                int rowCount = customerService.updateByParameter(item);
                if (rowCount > 0) {
                    customerList.add(item);
                }
            }
            if (customerList.size() == idArray.length) {
                // schedule email task
                String configPath = (String) request.getServletContext().getAttribute(Constant.EMAIL_CONFIG_PATH);
                EmailScheduleJob.scheduleTask(customerList, configPath, customer.getStringId(), customer.getSendTime());

                map.put("result", StateCode.SUCCESS);
                map.put("customerList", customerList);
            }
        }

        return JSONResponseUtil.getResult(map);
    }
}
