package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BillDto implements Serializable {

    @JsonProperty("idBill")
    private String idBill;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("created")
    private Date created;
    @JsonProperty("receipt")
    private ReceiptContainerDto receipt;
    @JsonProperty("listUsersPaid")
    private List<PaymentContainerDto> listUsersPaid;

}
