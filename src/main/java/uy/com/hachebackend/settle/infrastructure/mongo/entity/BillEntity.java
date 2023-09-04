package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(value = "bills")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {

    @Id
    private String idBill;
    @Indexed
    private String idMeet;

    private String owner;
    private String reference;
    private Date created;
    private ReceiptContainerEntity receipt;
    private List<PaymentContainerEntity> listUsersPaid = new ArrayList<>();

}
