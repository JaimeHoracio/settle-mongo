package uy.com.hachebackend.settle.infrastructure.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto implements Serializable {

    private String message;
    private Integer codeError;
}
