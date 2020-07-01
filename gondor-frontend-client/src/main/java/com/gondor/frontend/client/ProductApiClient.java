package com.gondor.frontend.client;

import com.gondor.frontend.dto.Product;
import com.gondor.frontend.fallbackfactory.ProductApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "GONDOR-API-SAMPLE", path = "/api/data/product", fallbackFactory = ProductApiFallbackFactory.class, contextId = "productApi")
public interface ProductApiClient {

    @GetMapping("/list")
    List<Product> productList();

    @GetMapping("/update/{id}")
    String exceptionTest(@PathVariable("id") Integer id);

}
