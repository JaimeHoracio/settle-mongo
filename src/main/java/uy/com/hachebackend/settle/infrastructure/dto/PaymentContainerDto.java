package uy.com.hachebackend.settle.infrastructure.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
public class PaymentContainerDto implements Serializable {
    private PaymentDto userPaid;
    private List<PaymentDto> listUsersDebt;
}
