package com.wbo.springkafkastarter.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CompteDto {
    private long id;
    private double amount;

    public CompteDto(double amount) {
        this.amount = amount;
    }
}
