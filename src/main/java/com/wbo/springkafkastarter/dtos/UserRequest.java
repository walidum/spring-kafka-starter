package com.wbo.springkafkastarter.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private long id;
    private String name;
    private String amounts;
}
