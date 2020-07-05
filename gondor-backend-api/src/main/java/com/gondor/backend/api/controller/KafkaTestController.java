package com.gondor.backend.api.controller;

import com.gondor.backend.service.GondorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/com/kafka/action")
public class KafkaTestController {

    private GondorService gondorService;

    @Autowired
    public KafkaTestController(GondorService gondorService) {
        this.gondorService = gondorService;
    }

    @GetMapping("/his/{partition}/{offset}")
    public String test(@PathVariable int partition, @PathVariable int offset) {
        gondorService.testKafka(partition, offset);
        return "测试kafka";
    }

}
