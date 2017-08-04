package com.darren.personal.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.darren.personal.util.DateUtil;
import com.darren.personal.util.ExcelFileWriterUtil;
import com.darren.personal.util.FileUtil;
import com.darren.personal.util.JSONResponseUtil;
import com.darren.personal.util.MailUtil;
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
                    item.setPhone(StringUtil.getHighLightString(item.getPhone(), customer.getPhone()));
                }
                if (!StringUtil.isEmpty(customer.getName())) {
                    item.setName(StringUtil.getHighLightString(item.getName(), customer.getName()));
                }
            }
        }

        model.put("customerList", customerList);

        return "anne";
    }

    @ResponseBody
    @RequestMapping(value = "/getCustomer.do")
    public Object getCustomer(Customer customer) {
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

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/editCustomer.do")
    public Object editCustomer(HttpServletRequest request, Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", 0);
        customer.setEmailState(Customer.EmailState.N.toString());
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
                    String taskId = String.valueOf(customer.getId());
                    // schedule task
                    long localSendTime = DateUtil.getLocalTime(sendTime);
                    String emailContent = MailUtil.getScheduleTemplate(customerList, localSendTime);
                    EmailScheduleJob.scheduleTask(emailContent, configPath, taskId, localSendTime);
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

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/addCustomer.do")
    public Object addCustomer(Customer customer) {
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

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteCustomer.do")
    public Object deleteCustomer(Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        int rowCount = customerService.deleteById(customer);
        map.put("result", rowCount);

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/batchSchedule.do")
    public Object batchSchedule(HttpServletRequest request, Customer customer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", StateCode.FAILURE);
        List<Customer> customerList = new ArrayList<Customer>();
        if (customer != null && !StringUtil.isEmpty(customer.getStringId())) {
            String[] idArray = customer.getStringId().split(",");
            for (String stringId : idArray) {
                Customer item = customerService.selectByPrimaryKey(Integer.valueOf(stringId.trim()));
                item.setSendTime(customer.getSendTime());
                item.setEmailState(Customer.EmailState.N.toString());
                int rowCount = customerService.updateByParameter(item);
                if (rowCount > 0) {
                    customerList.add(item);
                }
            }
            if (customerList.size() == idArray.length) {
                // schedule email task
                String configPath = (String) request.getServletContext().getAttribute(Constant.EMAIL_CONFIG_PATH);
                long localSendTime = DateUtil.getLocalTime(customer.getSendTime());
                String emailContent = MailUtil.getScheduleTemplate(customerList, localSendTime);
                EmailScheduleJob.scheduleTask(emailContent, configPath, customer.getStringId(), localSendTime);

                map.put("result", StateCode.SUCCESS);
                map.put("customerList", customerList);
            }
        }

        return map;
    }

    @RequestMapping(value = "/export.do")
    public String export(HttpServletRequest request, HttpServletResponse response) {
        String targetPath = request.getServletContext().getRealPath("upload");
        FileUtil.createFolder(targetPath);

        List<String> title = new ArrayList<String>();
        title.add("Phone");
        title.add("Name");
        title.add("Comment");
        List<Customer> customerList = customerService.selectAll();
        List<String> content = new ArrayList<String>();
        for (Customer customer : customerList) {
            StringBuilder builder = new StringBuilder();
            builder.append(customer.getPhone()).append(Constant.DELIMITER);
            builder.append(customer.getName()).append(Constant.DELIMITER);
            builder.append(customer.getComment());

            content.add(builder.toString());
        }
        String filePath = targetPath + "/" + new Date().getTime() + ".xlsx";
        try {
            boolean result = ExcelFileWriterUtil.writeExcel(filePath, title, content);
            if (result) {
                result = FileUtil.download(filePath, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        // Please notice the return value, if not null, it will show
        // getOutputStream() has already been called for this response
        return null;
    }
}
