package com.gondor.backend.service;

import com.gondor.backend.pojo.Customer;
import com.gondor.backend.pojo.Product;
import com.gondor.backend.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GondorService extends BaseService {

    public GondorService() {
    }

    public List<Product> getProductList() {
        return productBaseDao.selectAll();
    }

    public List<Customer> getCustomerList() {
        return customerBaseDao.selectAll();
    }

}
