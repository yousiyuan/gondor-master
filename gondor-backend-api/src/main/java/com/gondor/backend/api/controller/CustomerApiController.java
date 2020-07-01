package com.gondor.backend.api.controller;

import com.gondor.backend.pojo.Customer;
import com.gondor.backend.service.GondorService;
import com.gondor.master.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data/customer")
public class CustomerApiController {

    private GondorService gondorService;

    @Autowired
    public CustomerApiController(GondorService gondorService) {
        this.gondorService = gondorService;
    }

    @GetMapping("/list")
    public List<Customer> customerList() {
        System.out.println(JsonUtils.to(new String[]{"z", "x", "b"}));
        return gondorService.getCustomerList();
    }

    @GetMapping("/update/{id}")
    public String exceptionTest(@PathVariable Integer id) {
        if (id == 1) {
            throw new RuntimeException("测试服务降级、熔断");
        }
        return String.valueOf(id);
    }

}
