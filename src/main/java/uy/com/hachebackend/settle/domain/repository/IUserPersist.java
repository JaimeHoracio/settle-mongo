package uy.com.hachebackend.settle.domain.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;

import java.util.List;

public interface IUserPersist {

    Mono<UserDomain> findUser(final String idUser);

    Mono<UserDomain> createUser(final String email, final String name, final String password, Boolean guest, final List<String> roles);

    Flux<MeetDomain> selectAllMeetSettle(final String idUser);

    Mono<MeetDomain> selectMeetSettle(final String idUser, final String idMeet);

    Mono<String> addMeetSettle(final MeetDomain meet);

    Mono<String> updateMeetSettle(final MeetDomain meet);

    Mono<String> closeMeetSettle(final String email, final String idMeet);

    Mono<Void> removeMeetSettle(final String email, final String idMeet);

    Mono<String> addBillToMeetSettle(final BillDomain bill);

    Mono<String> updateBillToMeetSettle(final BillDomain bill);

    Mono<String> removeBillToMeetSettle(final String idBill);

    Flux<BillDomain> selectAllBillSettle(final String idMeet);
}
