package uy.com.hachebackend.settle.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettleDomain {

    private List<MeetDomain> listMeet = new ArrayList<>();
}
