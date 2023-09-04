package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.mongo.*;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.domain.repository.IUserPersist;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.BillEntity;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.SettleEntity;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SettleRepositoryImpl implements IUserPersist {

    private final IUserRepository userRepository;
    private final IMeetRepository meetRepository;
    private final IBillRepository billRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UserDomain> findUser(String idUser) {
        return userRepository.findByIdUser(idUser)
                .map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<UserDomain> createUser(String idUser, String name, String password, Boolean guest, List<String> roles) {

        UserEntity user = UserEntity.builder()
                .idUser(idUser)
                .name(name)
                .password(password)
                .dateCreate(new Date())
                .guest(Objects.nonNull(guest) ? guest : true)
                .settle(SettleEntity.builder().count_meets(0).total_debt(0f).total_debt(0f).build())
                .roles(roles)
                .build();
        return userRepository.save(user).map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    public Flux<MeetDomain> selectAllMeetSettle(final String idUser) {
        Query query = new Query(Criteria.where("idUser").is(idUser));

        return mongoTemplate.find(query, MeetEntity.class)
                .map(MeetMapper.INSTANCE::convertEntityToDomainMongo)
                .onErrorResume((error) -> {
                    log.error(">>>>> Error finding Meet Select: {}", error.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<MeetDomain> selectMeetSettle(final String idUser, final String idMeet) {
        return selectMeetUser(idUser, idMeet)
                .map(MeetMapper.INSTANCE::convertEntityToDomainMongo)
                .onErrorResume((error) -> {
                    log.error(">>>>> Error finding Meet Select: {}", error.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<String> addMeetSettle(MeetDomain meetDomain) {
        // Nota: Cuidado aqui con el orden de switchIfEmpty
        return selectMeetUser(meetDomain.getIdUser(), meetDomain.getIdMeet())
                .flatMap(m -> Mono.error(new Exception("Meet ya existe.")))
                .switchIfEmpty(Mono.defer(() -> {
                    MeetEntity meet = MeetMapper.INSTANCE.convertDomainToEntityMongo(meetDomain);
                    meet.setId(UUID.randomUUID().toString());
                    meet.setActive(true);
                    meet.setCreated(new Date());
                    meet.setUpdated(new Date());
                    return meetRepository
                            .save(meet);
                }))
                .map(mi -> "ok");
    }

    @Override
    public Mono<String> updateMeetSettle(final MeetDomain meetDomain) {
        return selectMeetUser(meetDomain.getIdUser(), meetDomain.getIdMeet())
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("Meet no encontrado: {} - {}", meetDomain.getIdUser(), meetDomain.getIdMeet());
                    return Mono.error(new Exception("Meet no encontrado."));
                }))
                .flatMap(meet -> {
                    meet.setName(meetDomain.getName());
                    meet.setUpdated(new Date());
                    return meetRepository.save(meet);
                })
                .map(mi -> "ok");
    }

    @Override
    public Mono<String> closeMeetSettle(String idUser, String idMeet) {
        return selectMeetUser(idUser, idMeet)
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("Meet no encontrado: {} - {}", idUser, idMeet);
                    return Mono.error(new Exception("Meet no encontrado."));
                }))
                .flatMap(meet -> {
                    meet.setActive(false);
                    meet.setUpdated(new Date());
                    return meetRepository.save(meet);
                })
                .map(mi -> "ok");
    }

    @Override
    public Mono<Void> removeMeetSettle(String idUser, String idMeet) {
        return selectMeetUser(idUser, idMeet)
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("Meet no encontrado: {} - {}", idUser, idMeet);
                    return Mono.error(new Exception("Meet no encontrado."));
                }))
                .flatMap(meetRepository::delete);
    }

    /*------------------- BILL --------------------*/
    @Override
    public Mono<String> addBillToMeetSettle(final BillDomain billDomain) {
        return selectBillMeet(billDomain.getIdBill(), billDomain.getIdMeet())
                .flatMap(m -> Mono.error(new Exception("Bill ya existe.")))
                .switchIfEmpty(Mono.defer(() -> {
                    BillEntity bill = BillMapper.INSTANCE.convertDomainToEntityMongo(billDomain);
                    bill.setCreated(new Date());
                    bill.setOwner(Objects.nonNull(billDomain.getOwner()) ?
                            billDomain.getOwner().getName() :
                            billDomain.getListUsersPaid().get(0).getUserPaid().getUser().getName());
                    return billRepository
                            .save(bill);
                }))
                .map(mi -> "ok");
    }

    @Override
    public Mono<String> updateBillToMeetSettle(BillDomain billDomain) {
        return selectBillMeet(billDomain.getIdBill(), billDomain.getIdMeet())
                .flatMap(m -> {
                    BillEntity bill = BillMapper.INSTANCE.convertDomainToEntityMongo(billDomain);
                    bill.setReference(billDomain.getReference());
                    bill.setReceipt(ReceiptContainerMapper.INSTANCE.convertDomainToEntityMongo(billDomain.getReceipt()));
                    bill.setListUsersPaid(PaymentContainerMapper.INSTANCE.convertDomainToEntityMongo(billDomain.getListUsersPaid()));
                    bill.setCreated(new Date());
                    return billRepository
                            .save(bill);
                })
                .switchIfEmpty(Mono.defer(() -> Mono.error(new Exception("Bill ya existe."))))
                .map(mi -> "ok");
    }

    @Override
    public Mono<String> removeBillToMeetSettle(String idBill) {
        return billRepository.findByIdBill(idBill)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new Exception("Bill no existe."))))
                .flatMap(billRepository::delete)
                .map(mi -> "ok");
    }

    public Flux<BillDomain> selectAllBillSettle(final String idMeet) {
        return billRepository.findByIdMeet(idMeet)
                .map(BillMapper.INSTANCE::convertEntityToDomainMongo)
                .onErrorResume((error) -> {
                    log.error(">>>>> Error finding Meet Select: {}", error.getMessage());
                    return Mono.empty();
                });
    }

    private Mono<MeetEntity> selectMeetUser(final String idUser, final String idMeet) {
        return meetRepository.findByIdUserAndIdMeet(idUser, idMeet);
    }

    private Mono<BillEntity> selectBillMeet(final String idMeet, final String idBill) {
        return billRepository.findByIdBillAndIdMeet(idMeet, idBill);
    }

}
