package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptContainerDto implements Serializable {
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("discount")
    private Double discount;
    @JsonProperty("currency")
    private CurrencyDto currency;
}
