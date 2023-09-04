package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
public class UserDto implements Serializable {

    @NotNull(message = "User can not be empty")
    @JsonProperty(value = "idUser")
    private String idUser;

    @NotNull(message = "Password can not be empty")
    @JsonProperty(value = "password")
    private String password;

    @JsonProperty("name")
    private String name;

    @JsonProperty("guest")
    private Boolean guest;

    @JsonProperty("dateCreate")
    private Date date_create;

    @JsonProperty("token")
    private String token;

    @JsonProperty("roles")
    private List<String> roles;

    @JsonProperty("settle")
    private SettleDto settle;
}
