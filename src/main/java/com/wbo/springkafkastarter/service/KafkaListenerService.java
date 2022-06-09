package com.wbo.springkafkastarter.service;

import com.wbo.springkafkastarter.dtos.UserDto;
import com.wbo.springkafkastarter.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListenerService {
    private ConcurrentHashMap<Long, UserDto> data = new ConcurrentHashMap();

    @Value("${topic.name}")
    private String topicName;

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

    public List<UserResponse> total() {
        return data.values().stream()
                .map(userDto -> new UserResponse(userDto, userDto.getComptes().stream().mapToDouble(value -> value.getAmount()).sum())).collect(Collectors.toList());
    }


}
