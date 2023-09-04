package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Document(value = "meets")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetEntity {

    @Id
    private String id;
    @Indexed
    private String idMeet;
    @Indexed
    private String idUser;

    private String owner;
    private String name;
    private Boolean active;
    private Date created;
    private Date updated;
}
