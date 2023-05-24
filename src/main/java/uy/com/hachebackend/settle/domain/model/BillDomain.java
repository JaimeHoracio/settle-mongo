package uy.com.hachebackend.settle.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDomain {

    private String idBill;
    private String reference;
    private Date created;
    private ReceiptContainerDomain receipt;
    private List<PaymentContainerDomain> listUsersPaid = new ArrayList<>();

}
