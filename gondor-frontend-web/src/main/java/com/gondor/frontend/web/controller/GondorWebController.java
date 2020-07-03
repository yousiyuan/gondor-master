package com.gondor.frontend.web.controller;

import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
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

    //TODO：Apollo在Java Config中的使用方式
    // @Value注解的正常用法，与读取本地配置无差异
    // *
    // *
    // Spring中支持读取配置文件的方式，Apollo同样都支持并“热刷新”配置
    // *
    // *
    // @ConfigurationProperties如果需要在Apollo配置变化时自动更新注入的值，需要配合使用EnvironmentChangeEvent或RefreshScope。

    /**
     * 使用@Value注解读取Apollo中的配置test.apollo.info，这里将@Value读取不到配置时的默认值设置为NULL
     */
    @Value("${test.apollo.info:NULL}")
    private String valueInApollo;

    /**
     * 用来把配置的json字符串自动注入为对象
     */
    @ApolloJsonValue("${apollo.json.test:null}")
    private Map<String, Object> json1;

    @Value("${apollo.bussines.info:NULL}")
    private String businessData;

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
    public Map<String, Object> testApollo() {
        Map<String, Object> map = new HashMap<>();
        map.put("test.apollo.info", valueInApollo);
        map.put("application", application.getPropertyNames());
        map.put("datasource", datasource.getPropertyNames());
        map.put("business", business.getPropertyNames());

        map.put("message", json1.get("message"));
        map.put("json.from.apollo", json1);

        System.out.println(businessData);

        return map;
    }

}
