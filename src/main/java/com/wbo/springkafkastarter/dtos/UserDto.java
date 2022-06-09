package com.wbo.springkafkastarter.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private long id;
    private String name;
    private List<CompteDto> comptes;

}
