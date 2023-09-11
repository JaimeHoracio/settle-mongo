package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import reactor.util.annotation.NonNull;
import uy.com.hachebackend.settle.domain.model.UserNameDomain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
public class BillDto implements Serializable {

    @NotNull(message = "Bill can not be empty")
    @JsonProperty("idBill")
    private String idBill;

    @NotNull(message = "Meet can not be empty")
    @JsonProperty("idMeet")
    private String idMeet;

    @NotNull(message = "Reference can not be empty")
    @JsonProperty("reference")
    private String reference;

    @JsonProperty("owner")
    private UserNameDto owner;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("updated")
    private Date updated;

    @JsonProperty("receipt")
    private ReceiptContainerDto receipt;

    @JsonProperty("listUsersPaid")
    private List<PaymentContainerDto> listUsersPaid;

}
