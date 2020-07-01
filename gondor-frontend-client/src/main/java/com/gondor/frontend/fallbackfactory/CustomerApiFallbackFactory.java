package com.gondor.frontend.fallbackfactory;

import com.gondor.frontend.client.CustomerApiClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class CustomerApiFallbackFactory implements FallbackFactory<CustomerApiClient> {

    @Override
    public CustomerApiClient create(Throwable throwable) {
        String[] stackTraceArray = Arrays.stream(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new);
        log.error("调用远程服务发生异常。\r\n异常消息：{}\r\n\r\tat {}", throwable.toString(), String.join("\r\n\r\tat ", stackTraceArray));
        return new CustomerApiFallback();
    }

}
