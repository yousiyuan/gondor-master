package com.gondor.frontend.utils;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * *
 * * 构建Feign工具类思路
 * *
 * * 大概需要四种构建FeignClient对象的方法：①get请求k-v；②post请求k-v；③get请求json报文；④post请求json报文
 * *
 * * 原生feign api使用方式，可参考：
 * * ① https://www.cnblogs.com/chenkeyu/p/9017996.html
 * * ② https://github.com/OpenFeign/feign
 * *
 */
@Component
@Import(FeignClientsConfiguration.class)
public class FeignUtils {

    private static Decoder decoder;
    private static Encoder encoder;

    @Autowired
    public FeignUtils(Decoder decoder, Encoder encoder) {
        FeignUtils.decoder = decoder;
        FeignUtils.encoder = encoder;
    }

    /**
     * 通过原生 Feign API 动态构建 基于java interface 的 FeignClient对象
     */
    public static <T> T createFeignClient(String baseUrl, Class<? extends T> clazz) {
        return Feign.builder().contract(new feign.Contract.Default())
                .encoder(encoder).decoder(decoder)
                .target(clazz, baseUrl);
    }

}
