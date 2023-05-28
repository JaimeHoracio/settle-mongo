package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MeetRequest implements Serializable {

    @JsonProperty("email")
    private String email;
    @JsonProperty("meet")
    private MeetDto meet;

}
