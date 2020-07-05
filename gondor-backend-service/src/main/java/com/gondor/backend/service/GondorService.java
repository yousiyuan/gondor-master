package com.gondor.backend.service;

import com.gondor.backend.pojo.Customer;
import com.gondor.backend.pojo.Product;
import com.gondor.backend.service.base.BaseService;
import com.gondor.backend.service.kafka.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GondorService extends BaseService {

    public GondorService() {
    }

    public List<Product> getProductList() {
        return productBaseDao.selectAll();
    }

    public List<Customer> getCustomerList() {
        return customerBaseDao.selectAll();
    }

    //测试：根据指定的offset进行消费kafka
    public void testKafka(int partition, int offset) {
        kafkaService.consumerHistory(KafkaConstant.TOPIC_GONDOR, partition, offset, (ConsumerRecord<String, Object> record) ->
                System.out.println(record.topic() + " - " + record.partition() + " - " + record.offset() + " - " + record.value())
        );
    }

}
