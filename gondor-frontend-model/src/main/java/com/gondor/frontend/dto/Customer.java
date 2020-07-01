package com.gondor.frontend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Customer implements Serializable {

    private String customerId;

    private String companyName;

    private String contactName;

    private String contactTitle;

    private String address;

    private String city;

    private String region;

    private String postalCode;

    private String country;

    private String phone;

    private String fax;

}