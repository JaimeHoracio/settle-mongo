package uy.com.hachebackend.settle.domain.model;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
public class UserDomain {

    private String idUser;

    private String email;

    private Boolean guest;

    private String name;

    private String password;

    private String token;

    private List<String> roles;

    private SettleDomain settle;

}
