package com.wbo.springkafkastarter.controller;

import com.wbo.springkafkastarter.dtos.CompteDto;
import com.wbo.springkafkastarter.dtos.UserDto;
import com.wbo.springkafkastarter.dtos.UserRequest;
import com.wbo.springkafkastarter.service.KafkaListenerService;
import com.wbo.springkafkastarter.service.KafkaPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final KafkaPublisherService kafkaPublisherService;
    private final KafkaListenerService kafkaListenerService;

    @PostMapping
    public ResponseEntity addUser(@RequestBody UserRequest request) {

        kafkaPublisherService.send(mapTo(request));
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> find(@PathVariable("id") final long id) {
        var user = kafkaListenerService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("amounts")
    public ResponseEntity users() {
        var amounts = kafkaListenerService.total();
        return ResponseEntity.ok(amounts);
    }

    private UserDto mapTo(UserRequest request) {
        var accounts = Arrays.stream(request.getAmounts().split(";"))
                .map(s -> new CompteDto(Double.parseDouble(s)))
                .collect(Collectors.toList());
        return new UserDto(request.getId(), request.getName(), accounts);
    }
}
