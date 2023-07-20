package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private String email;

    private String name;

    private String password;

    private Boolean guest;

    private List<String> roles;

    private SettleEntity settle;
}
