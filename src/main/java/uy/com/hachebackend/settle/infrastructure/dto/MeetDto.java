package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
public class MeetDto implements Serializable {

    @NotNull(message = "Meet can not be empty")
    @JsonProperty("idMeet")
    private String idMeet;

    @NotNull(message = "User can not be empty")
    @JsonProperty("idUser")
    private String idUser;

    @NotNull(message = "Name meet can not be empty")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "User who create meet can not be empty")
    @JsonProperty("owner")
    private UserNameDto owner;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("updated")
    private Date updated;

}
