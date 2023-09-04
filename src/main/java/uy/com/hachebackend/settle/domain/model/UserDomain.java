package uy.com.hachebackend.settle.domain.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {

    private String idUser;

    private Boolean guest;

    private String name;

    private String password;

    private Date date_create;

    private String token;

    private List<String> roles;

    private SettleDomain settle;

}
