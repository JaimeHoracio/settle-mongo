package uy.com.hachebackend.settle.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentContainerDomain {
    private PaymentDomain userPaid;
    private List<PaymentDomain> listUsersDebt = new ArrayList<>();
}
