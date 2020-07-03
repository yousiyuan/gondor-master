package com.gondor.frontend.web.controller;

import com.gondor.frontend.dto.Customer;
import com.gondor.frontend.dto.Product;
import com.gondor.frontend.web.controller.base.SuperController;
import com.gondor.master.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/test")
public class GondorWebController extends SuperController {

    private static final String KEY = "3BFBZ-ZKD3X-LW54A-ZT76D-E7AHO-4RBD5";

    //TODO：
    // 使用@Value注解读取Apollo中的配置test.apollo.info
    // 这里将@Value读取不到配置时的默认值设置为NULL
    @Value("${test.apollo.info:NULL}")
    private String valueInApollo;

    @GetMapping("/p/list")
    @ResponseBody
    public List<Product> productTest() {
        return productApiClient.productList();
    }

    @GetMapping("/c/list")
    @ResponseBody
    public List<Customer> customerTest() {
        return customerApiClient.customerList();
    }

    @GetMapping("/ip")
    @ResponseBody
    public String ipInfoTest() {
        String str = thirdPartyApiClient.queryIpAddressInfo(KEY);
        System.out.println(JsonUtils.format(str));
        return str;
    }

    @GetMapping("/ex/{id}")
    @ResponseBody
    public String exceptionTest1(@PathVariable Integer id) {
        return productApiClient.exceptionTest(id);
    }

    @GetMapping("/err/{id}")
    @ResponseBody
    public String exceptionTest2(@PathVariable Integer id) {
        return customerApiClient.exceptionTest(id);
    }

    @GetMapping("/apollo")
    @ResponseBody
    public Map<String, Object> apolloTest() {
        Map<String, Object> apolloConfig = new HashMap<>();
        apolloConfig.put("test.apollo.info", valueInApollo);
        return apolloConfig;
    }

}
