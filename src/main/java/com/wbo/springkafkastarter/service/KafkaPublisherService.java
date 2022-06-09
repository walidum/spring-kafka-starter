package com.wbo.springkafkastarter.service;

import com.wbo.springkafkastarter.dtos.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaPublisherService {
    @Value("${topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<Long, Object> kafkaTemplate;

    public void send(UserDto user) {
        log.info("Payload : {}", user.toString());
        kafkaTemplate.send(topicName, user);
    }
}
