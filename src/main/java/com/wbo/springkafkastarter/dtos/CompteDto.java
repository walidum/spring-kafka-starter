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
    public static int cpt = 0;
    private long id;
    private double amount;

    public CompteDto(double amount) {
        cpt++;
        this.id = cpt;
        this.amount = amount;
    }
}
