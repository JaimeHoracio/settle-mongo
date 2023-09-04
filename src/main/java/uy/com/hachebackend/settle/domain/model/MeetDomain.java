package uy.com.hachebackend.settle.domain.model;

import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetDomain {

    private String idMeet;
    private String idUser;
    private UserNameDomain owner;
    private Boolean active;
    private String name;
    private Date created;
    private Date updated;

}
