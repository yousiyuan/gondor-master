package com.gondor.frontend.client;

import com.gondor.frontend.dto.Customer;
import com.gondor.frontend.fallbackfactory.CustomerApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "GONDOR-API-SAMPLE", path = "/api/data/customer", fallbackFactory = CustomerApiFallbackFactory.class, contextId = "customerApi")
public interface CustomerApiClient {

    @GetMapping("/list")
    List<Customer> customerList();

    @GetMapping("/update/{id}")
    String exceptionTest(@PathVariable("id") Integer id);

}
