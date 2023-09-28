package uy.com.hachebackend.settle.domain.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.BillEntity;

public interface ISettlePersist {

    Mono<UserDomain> selectUserSettle( String idUser);

    Mono<UserDomain> saveUserSettle(UserDomain user);

    Flux<MeetDomain> selectAllMeetSettle( String idUser, Boolean active);

    Flux<MeetDomain> selectMeetSettle( String idMeet);

    Mono<MeetDomain> saveMeetSettle( MeetDomain meet);

    Mono<MeetDomain> updateMeetSettle( MeetDomain meet);

    Mono<Void> removeMeetSettle(String idMeet, String idUser);

    Mono<MeetDomain> selectMeetByIdMeetAndIdUserSettle( String idMeet,  String idUser);

    Mono<BillDomain> saveBillSettle( BillDomain bill);

    Mono<BillDomain> selectBillByIdBillAndIdMeetSettle( String idBill,  String idMeet);

    Mono<Void> removeBillSettle( String idBill);

    Flux<BillEntity> removeBillsByIdMeetSettle( String idMeet);

    Flux<BillDomain> selectBillByIdMeetSettle( String idMeet);
}
