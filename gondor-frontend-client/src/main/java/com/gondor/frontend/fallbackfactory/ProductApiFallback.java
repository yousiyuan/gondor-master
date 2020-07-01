package com.gondor.frontend.fallbackfactory;

import com.gondor.frontend.client.ProductApiClient;
import com.gondor.frontend.dto.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductApiFallback implements ProductApiClient {

    @Override
    public List<Product> productList() {
        log.error("远程服务降级执行。\r\n\r\tat {}", Thread.currentThread().getStackTrace()[1].toString());
        return new ArrayList<>();
    }

    @Override
    public String exceptionTest(Integer id) {
        log.error("远程服务降级执行。\r\n\r\tat {}", Thread.currentThread().getStackTrace()[1].toString());
        return null;
    }

}
