package uy.com.hachebackend.settle.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class BillRequest implements Serializable {
    private String idUser;
    private String idMeet;
    private BillDto bill;

}
