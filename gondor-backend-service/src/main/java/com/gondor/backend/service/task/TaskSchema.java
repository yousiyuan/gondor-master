package com.gondor.backend.service.task;

import com.gondor.backend.dao.base.BaseDao;
import com.gondor.backend.pojo.Product;
import com.gondor.backend.service.kafka.KafkaService;
import com.gondor.backend.service.kafka.KafkaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 使用定时任务简单测试kafka
 */
@Component
public class TaskSchema {

    private KafkaService kfkProducer;
    private BaseDao<Product> productBaseDao;

    @Autowired
    public TaskSchema(KafkaService kfkProducer,
                      BaseDao<Product> productBaseDao) {
        this.kfkProducer = kfkProducer;
        this.productBaseDao = productBaseDao;
    }

    @Async
    @Scheduled(cron = "0 */1 * * * ?")
    public void kafkaTest1() {
        Product product = productBaseDao.selectByPrimaryKey((int) (Math.random() * 100));
        while (product == null) {
            product = productBaseDao.selectByPrimaryKey((int) (Math.random() * 100));
        }
        kfkProducer.produce(KafkaConstant.TOPIC_GONDOR, product);
    }

//    @Async
//    @Scheduled(cron = "30 * * * * ?")
//    public void kafkaTest2() {
//        kfkProducer.produce(KafkaConstant.TOPIC_GONDOR, ComUtils.formatDateObj(new Date()));
//    }

}
