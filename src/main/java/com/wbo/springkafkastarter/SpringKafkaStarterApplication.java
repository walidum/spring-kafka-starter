package com.wbo.springkafkastarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SpringKafkaStarterApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringKafkaStarterApplication.class);

    public static void main(String[] args) {
        logger.info("walid");
        SpringApplication.run(SpringKafkaStarterApplication.class, args);
    }

}
