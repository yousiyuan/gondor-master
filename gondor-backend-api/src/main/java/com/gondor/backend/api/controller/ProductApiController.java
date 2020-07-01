package com.gondor.backend.api.controller;

import com.gondor.backend.pojo.Product;
import com.gondor.backend.service.GondorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data/product")
public class ProductApiController {

    private GondorService gondorService;

    @Autowired
    public ProductApiController(GondorService gondorService) {
        this.gondorService = gondorService;
    }

    @GetMapping("/list")
    public List<Product> productList() {
        return gondorService.getProductList();
    }

    @GetMapping("/update/{id}")
    public String exceptionTest(@PathVariable Integer id) {
        if (id == 1) {
            throw new RuntimeException("测试服务降级、熔断");
        }
        return String.valueOf(id);
    }

}
