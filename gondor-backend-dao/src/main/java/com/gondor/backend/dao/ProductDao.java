package com.gondor.backend.dao;

import com.gondor.backend.dao.mapper.ProductMapper;
import com.gondor.backend.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private ProductMapper productMapper;

    @Autowired
    public ProductDao(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> selectProductByCategory(Integer categoryId) {
        return productMapper.selectProductByCategory(categoryId);
    }

}
