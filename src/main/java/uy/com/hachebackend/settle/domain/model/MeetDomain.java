package uy.com.hachebackend.settle.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetDomain {

    private String idMeet;
    private Boolean active;
    private String name;
    private Date created;
    private Date updated;
    private List<BillDomain> listBill = new ArrayList<>();
}
