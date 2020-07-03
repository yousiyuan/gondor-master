package com.gondor.frontend.web.controller.base;

import com.ctrip.framework.apollo.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gondor.frontend.client.CustomerApiClient;
import com.gondor.frontend.client.ProductApiClient;
import com.gondor.frontend.client.ThirdPartyApiClient;
import com.gondor.frontend.web.constant.ApolloConstant;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SuperController {

    @Autowired
    protected ProductApiClient productApiClient;

    @Autowired
    protected CustomerApiClient customerApiClient;

    @Autowired
    protected ThirdPartyApiClient thirdPartyApiClient;

    @Autowired
    protected Config application;

    @Autowired
    protected Config datasource;

    @Autowired
    protected Config business;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ApolloConstant apolloConstant;

}
