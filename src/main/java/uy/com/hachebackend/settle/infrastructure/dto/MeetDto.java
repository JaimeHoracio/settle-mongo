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
public class MeetDto implements Serializable {

    private String idMeet;
    private Boolean active;
    private String name;
    private Date created;
    private Date updated;
    private List<BillDto> listBill;
}
