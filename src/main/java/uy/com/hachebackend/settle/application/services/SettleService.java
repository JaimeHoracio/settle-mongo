package uy.com.hachebackend.settle.application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.domain.repository.IUserPersist;

@Service
@Slf4j
public class SettleService {

    public Mono<UserDomain> addMeetSettle(final String idUser, final MeetDomain meet, final IUserPersist userPesist) {
        return userPesist.addMeetSettle(idUser, meet);
    }

    public Mono<UserDomain> updateMeetSettle(final String idUser, final MeetDomain meet, final IUserPersist userPesist) {
        return userPesist.updateMeetSettle(idUser, meet);
    }

    public Mono<UserDomain> closeMeetSettle(final String idUser, final String idMeet, final IUserPersist userPesist) {
        return userPesist.closeMeetSettle(idUser, idMeet);
    }

    public Mono<UserDomain> removeMeetSettle(final String idUser, final String idMeet, final IUserPersist userPesist) {
        return userPesist.removeMeetSettle(idUser, idMeet);
    }

    public Mono<UserDomain> addBillListMeetSettle(final String idUser, final String idMeet, final BillDomain bill, final IUserPersist userPesist) {
        return userPesist.addBillListMeetSettle(idUser, idMeet, bill);
    }

    public Mono<UserDomain> updateBillSettle(final String idUser, final String idMeet, final BillDomain bill, final IUserPersist userPesist) {
        return userPesist.updateBillListMeetSettle(idUser, idMeet, bill);
    }

    public Mono<UserDomain> removeBillSettle(final String idUser, final String idMeet, final String idBill, final IUserPersist userPesist) {
        return userPesist.removeBillListMeetSettle(idUser, idMeet, idBill);
    }

}
