package uy.com.hachebackend.settle.domain.model;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDomain {
    private Double amount;
    private UserNameDomain user;
}
