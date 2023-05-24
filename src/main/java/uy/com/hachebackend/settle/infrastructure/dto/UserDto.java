package uy.com.hachebackend.settle.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ToString
public class UserDto implements Serializable {

    private String idUser;

    private String email;

    private Boolean guest;

    private String name;

    private String password;

    private String token;

    private List<String> roles;

    private SettleDto settle;
}
