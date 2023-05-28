package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest implements Serializable {

    @JsonProperty("email")
    private String email;
    @JsonProperty("idMeet")
    private String idMeet;
    @JsonProperty("bill")
    private BillDto bill;

}
