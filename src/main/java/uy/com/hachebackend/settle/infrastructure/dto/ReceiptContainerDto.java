package uy.com.hachebackend.settle.infrastructure.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
public class ReceiptContainerDto implements Serializable {

    private Double amount;
    private Double discount;
    private CurrencyDto currency;
}
