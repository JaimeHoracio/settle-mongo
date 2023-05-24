package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    private Double amount;
    private UserSimpleEntity user;
}
