package com.gondor.frontend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {

    private Integer productId;

    private String productName;

    private Integer supplierId;

    private Integer categoryId;

    private String quantityPerUnit;

    private Double unitPrice;

    private Integer unitsInStock;

    private Integer unitsOnOrder;

    private Integer reorderLevel;

    private Byte discontinued;
}
