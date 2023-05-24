package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentContainerEntity {
    private PaymentEntity userPaid;
    private List<PaymentEntity> listUsersDebt = new ArrayList<>();
}
