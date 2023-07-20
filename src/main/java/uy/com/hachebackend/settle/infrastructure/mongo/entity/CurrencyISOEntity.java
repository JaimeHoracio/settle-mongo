package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("currencyISO")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyISOEntity {

    @Id
    private String code;
    private String name;
    private Integer num;
    private String country;
}
