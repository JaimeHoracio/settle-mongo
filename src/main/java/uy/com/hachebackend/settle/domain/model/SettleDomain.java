package uy.com.hachebackend.settle.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettleDomain {

    private Float total_paid;
    private Float total_debt;
    private Integer count_meets;
}
