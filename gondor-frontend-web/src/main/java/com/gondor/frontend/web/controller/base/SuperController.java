package com.gondor.frontend.web.controller.base;

import com.gondor.frontend.client.CustomerApiClient;
import com.gondor.frontend.client.ProductApiClient;
import com.gondor.frontend.client.ThirdPartyApiClient;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SuperController {

    @Autowired
    protected ProductApiClient productApiClient;

    @Autowired
    protected CustomerApiClient customerApiClient;

    @Autowired
    protected ThirdPartyApiClient thirdPartyApiClient;

}
