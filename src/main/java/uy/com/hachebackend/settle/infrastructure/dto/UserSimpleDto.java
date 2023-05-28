package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleDto implements Serializable {
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
}
