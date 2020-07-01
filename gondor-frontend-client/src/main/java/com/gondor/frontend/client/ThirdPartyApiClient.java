package com.gondor.frontend.client;

import com.gondor.frontend.fallbackfactory.ThirdPartyApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "thirdPartyApiClient", url = "https://apis.map.qq.com/ws", fallbackFactory = ThirdPartyApiFallbackFactory.class)
public interface ThirdPartyApiClient {

    @GetMapping("/location/v1/ip")
    String queryIpAddressInfo(@RequestParam("key") String key);

}
