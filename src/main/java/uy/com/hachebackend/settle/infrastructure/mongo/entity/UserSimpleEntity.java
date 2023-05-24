package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleEntity {

    private String idUser;
    private String name;
}
