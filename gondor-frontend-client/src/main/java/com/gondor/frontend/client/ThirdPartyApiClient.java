package com.gondor.frontend.client;

//import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ThirdPartyApiClient {

    //@Headers("Content-Type: application/json")
    @RequestLine("GET /location/v1/ip?key={key}")
    String queryIpAddressInfo(@Param("key") String key);

}
