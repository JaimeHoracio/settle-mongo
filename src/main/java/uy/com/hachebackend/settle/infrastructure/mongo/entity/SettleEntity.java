package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SettleEntity implements Serializable {

    private Float total_paid;
    private Float total_debt;
    private Integer count_meets;

}
