package uy.com.hachebackend.settle.domain.model;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleDomain {

    private String email;
    private String name;
}
