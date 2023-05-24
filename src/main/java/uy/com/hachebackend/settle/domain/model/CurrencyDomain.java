package uy.com.hachebackend.settle.domain.model;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDomain {

    private String name;
    private String code;
    private Integer num;
    private String country;
}
