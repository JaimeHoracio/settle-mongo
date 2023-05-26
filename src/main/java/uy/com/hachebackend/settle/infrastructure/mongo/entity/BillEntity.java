package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {

    private String idBill;
    private String idMeet;
    private String reference;
    private Date created;
    private ReceiptContainerEntity receipt;
    private List<PaymentContainerEntity> listUsersPaid = new ArrayList<>();

}
