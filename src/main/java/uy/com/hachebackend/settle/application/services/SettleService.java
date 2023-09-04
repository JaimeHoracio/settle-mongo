package uy.com.hachebackend.settle.application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.repository.IUserPersist;

import java.util.List;

@Service
@Slf4j
public class SettleService {

    //Meets
    public Mono<List<MeetDomain>> selectAllMeetSettle(final String idUser, final IUserPersist userPesist) {
        return userPesist.selectAllMeetSettle(idUser).collectList();
    }

    public Mono<MeetDomain> selectMeetSettle(final String idUser, final String idMeet, final IUserPersist userPesist) {
        return userPesist.selectMeetSettle(idUser, idMeet);
    }

    public Mono<String> addMeetSettle(final MeetDomain meet, final IUserPersist userPesist) {
        return userPesist.addMeetSettle(meet);
    }

    public Mono<String> updateMeetSettle(final MeetDomain meet, final IUserPersist userPesist) {
        return userPesist.updateMeetSettle(meet);
    }

    public Mono<String> closeMeetSettle(final String email, final String idMeet, final IUserPersist userPesist) {
        return userPesist.closeMeetSettle(email, idMeet);
    }

    public Mono<Void> removeMeetSettle(final String email, final String idMeet, final IUserPersist userPesist) {
        return userPesist.removeMeetSettle(email, idMeet);
    }

    //Bills
    public Mono<String> addBillToMeetSettle(final BillDomain bill, final IUserPersist userPesist) {
        return userPesist.addBillToMeetSettle(bill);
    }

    public Mono<String> updateBillSettle(final BillDomain bill, final IUserPersist userPesist) {
        return userPesist.updateBillToMeetSettle(bill);
    }

    public Mono<String> removeBillSettle(final String idBill, final IUserPersist userPesist) {
        return userPesist.removeBillToMeetSettle(idBill);
    }

    public Mono<List<BillDomain>> selectAllBillSettle(final String idMeet, final IUserPersist userPesist) {
        return userPesist.selectAllBillSettle(idMeet).collectList();
    }

}
