package uy.com.hachebackend.settle.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class CurrencyDto implements Serializable {
    private String code;
    private String name;
    private Integer num;
    private String country;
}
