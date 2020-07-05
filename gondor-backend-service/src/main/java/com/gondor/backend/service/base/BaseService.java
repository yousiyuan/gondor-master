package com.gondor.backend.service.base;

import com.gondor.backend.dao.ProductDao;
import com.gondor.backend.dao.base.BaseDao;
import com.gondor.backend.pojo.Customer;
import com.gondor.backend.pojo.Product;
import com.gondor.backend.service.kafka.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected ProductDao productDao;

    @Autowired
    protected BaseDao<Product> productBaseDao;

    @Autowired
    protected BaseDao<Customer> customerBaseDao;

    @Autowired
    protected KafkaService kafkaService;

}
