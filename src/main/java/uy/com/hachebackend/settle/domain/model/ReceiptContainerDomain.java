package uy.com.hachebackend.settle.domain.model;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptContainerDomain {

    private Double amount;
    private Double discount;
    private CurrencyDomain currency;
}
