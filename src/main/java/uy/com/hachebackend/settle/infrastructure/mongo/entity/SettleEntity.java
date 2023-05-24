package uy.com.hachebackend.settle.infrastructure.mongo.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettleEntity {

    private List<MeetEntity> listMeet = new ArrayList<>();
}
