package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto implements Serializable {

    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("user")
    private UserNameDto user;
}
