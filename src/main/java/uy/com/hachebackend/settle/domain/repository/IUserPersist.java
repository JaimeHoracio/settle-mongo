package uy.com.hachebackend.settle.domain.repository;

import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;

import java.util.List;

public interface IUserPersist {

    Mono<UserDomain> findUser(final String idUses);

    Mono<UserDomain> createUser(final String email, final String name, final String password, final List<String> roles);

    Mono<UserDomain> updateUser(final UserDomain user);

    Mono<UserDomain> addMeetSettle(final String email, final MeetDomain meet);

    Mono<UserDomain> updateMeetSettle(final String email, final MeetDomain meet);

    Mono<UserDomain> closeMeetSettle(final String email, final String idMeet);

    Mono<UserDomain> removeMeetSettle(final String email, final String idMeet);

    Mono<UserDomain> addBillListMeetSettle(final String email, final String meet, final BillDomain bill);

    Mono<UserDomain> updateBillListMeetSettle(final String email, final String meet, final BillDomain bill);

    Mono<UserDomain> removeBillListMeetSettle(final String email, final String meet, final String idBill);


}
