package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uy.com.hachebackend.settle.infrastructure.dto.SettleDto;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private String id;

    @Indexed
    private String idUser;

    private String name;
    private String password;
    private Boolean guest;
    private Date dateCreate;
    private List<String> roles;
    private SettleEntity settle;
}
