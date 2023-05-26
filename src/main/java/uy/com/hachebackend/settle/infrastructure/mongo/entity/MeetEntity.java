package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetEntity {

    private String idMeet;
    private Boolean active;
    private String name;
    private Date created;
    private Date updated;
    private ArrayList<BillEntity> listBill = new ArrayList<>();
}
