package uy.com.hachebackend.settle.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
public class BillDto implements Serializable {

    private String idBill;
    private String reference;
    private Date created;
    private ReceiptContainerDto receipt;
    private List<PaymentContainerDto> listUsersPaid;

}
