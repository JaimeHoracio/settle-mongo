package uy.com.hachebackend.settle.domain.repository;

import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;

import java.util.List;

public interface IUserPersist {

    Mono<UserDomain> findUser(final String idUses);

    Mono<UserDomain> saveUser(final String idUser, final String name, final String password, final List<String> roles);

    Mono<UserDomain> addMeetSettle(final String idUser, final MeetDomain meet);

    Mono<UserDomain> updateMeetSettle(final String idUser, final MeetDomain meet);

    Mono<UserDomain> closeMeetSettle(final String idUser, final String idMeet);

    Mono<UserDomain> removeMeetSettle(final String idUser, final String idMeet);

    Mono<UserDomain> addBillListMeetSettle(final String idUser, final String meet, final BillDomain bill);

    Mono<UserDomain> updateBillListMeetSettle(final String idUser, final String meet, final BillDomain bill);

    Mono<UserDomain> removeBillListMeetSettle(final String idUser, final String meet, final String idBill);


}
