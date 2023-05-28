package uy.com.hachebackend.settle.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MeetDto implements Serializable {

    @JsonProperty("idMeet")
    private String idMeet;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("name")
    private String name;
    @JsonProperty("created")
    private Date created;
    @JsonProperty("updated")
    private Date updated;
    @JsonProperty("listBill")
    private List<BillDto> listBill;
}
