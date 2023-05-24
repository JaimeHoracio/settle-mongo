package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity {

    private String name;
    private String codeISO;
    private Integer numISO;
    private String country;
}
