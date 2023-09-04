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
public class SettleDto implements Serializable {

    @JsonProperty("totalPaid")
    private Float total_paid;

    @JsonProperty("totalDebt")
    private Float total_debt;

    @JsonProperty("countMeets")
    private Integer count_meets;

}
