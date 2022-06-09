package com.wbo.springkafkastarter.service;

import com.wbo.springkafkastarter.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListenerService {
    private ConcurrentHashMap<Long, UserDto> data = new ConcurrentHashMap();

    @KafkaListener(topics = "${topic.name}", groupId = "group-3")
    public void consume(ConsumerRecord<Long, UserDto> payload) {
        log.info("Consumer Key {}", payload.key());
        log.info("Consumer Value {}", payload.value());
        data.put(payload.value().getId(), payload.value());
    }

    public UserDto findById(Long id) {
        var exist = data.keySet().stream().filter(item -> item == id).findFirst();
        return exist.isPresent() ? data.get(exist.get()) : null;
    }

    public Map<Long, Double> total() {
        return data.values().stream()
                .collect(Collectors.toMap(UserDto::getId,
                        userDto -> userDto.getComptes().stream().mapToDouble(value -> value.getAmount()).sum()));
    }

    public ConcurrentHashMap<Long, UserDto> getData() {
        return data;
    }
}
