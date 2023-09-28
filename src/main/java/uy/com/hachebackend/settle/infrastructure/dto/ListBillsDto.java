package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListBillsDto implements Serializable {

    @JsonProperty("listBills")
    private List<BillDto> listBills;
}
