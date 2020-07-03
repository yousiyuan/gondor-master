package com.gondor.frontend.fallbackfactory;

import com.gondor.frontend.client.ThirdPartyApiClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThirdPartyApiFallback implements ThirdPartyApiClient {

    @Override
    public String queryIpAddressInfo(String key) {
        log.error("远程服务降级执行。\r\n\r\tat {}", Thread.currentThread().getStackTrace()[1].toString());
        return " ";
    }

}
