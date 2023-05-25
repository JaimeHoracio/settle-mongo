package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.BillMapper;
import uy.com.hachebackend.settle.application.mapper.MeetMapper;
import uy.com.hachebackend.settle.application.mapper.UserMapper;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.domain.repository.IUserPersist;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class SettleRepositoryImpl implements IUserPersist {

    private final ISettleRepository userRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UserDomain> findUser(String email) {
        return userRepository.findById(email)
                .map(UserMapper.INSTANCE::convertEntityToDomain);
    }

    @Override
    public Mono<UserDomain> saveUser(String email, String name, String password, List<String> roles) {
        UserEntity user = UserEntity.builder().email(email).name(name).password(password).roles(roles).build();
        return userRepository.save(user).map(UserMapper.INSTANCE::convertEntityToDomain);
    }

    @Override
    public Mono<UserDomain> addMeetSettle(String idUser, MeetDomain meet) {
        Query query = new Query(Criteria.where("_id").is(idUser));
        Update addMeet = new Update().addToSet("settle.listMeet", MeetMapper.INSTANCE.convertDomainToEntity(meet));

        return mongoTemplate.findAndModify(query, addMeet, UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomain);
    }

    @Override
    public Mono<UserDomain> updateMeetSettle(String idUser, MeetDomain meet) {
        return updateMeetDB(idUser, MeetMapper.INSTANCE.convertDomainToEntity(meet)).map(UserMapper.INSTANCE::convertEntityToDomain);
    }

    public Mono<UserEntity> updateMeetDB(String idUser, MeetEntity meet) {
        Query query = new Query(Criteria.where("_id").is(idUser))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(meet.getIdMeet()));

        Update updateMeet = new Update().set("settle.listMeet.$", meet);

        return mongoTemplate.findAndModify(query, updateMeet, UserEntity.class);
    }

    @Override
    public Mono<UserDomain> closeMeetSettle(String idUser, String idMeet) {
        Query query = new Query(Criteria.where("_id").is(idUser))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet));

        Update closeMeet = new Update().set("settle.listMeet.$.active", false).set("settle.listMeet.$.updated", new Date());

        return mongoTemplate.findAndModify(query, closeMeet, UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomain);
        /*
         return mongoTemplate.findOne(query, MeetEntity.class)
                .flatMap(meet -> {
                    meet.setActive(false);
                    meet.setUpdated(new Date());
                    return updateMeetDB(idUser, meet).map(UserMapper.INSTANCE::convertEntityToDomain);
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error finding Meet Manual: {}", error.getMessage());
                    return Mono.empty();
                });
        */
    }

    @Override
    public Mono<UserDomain> removeMeetSettle(String idUser, String idMeet) {
        Query query = new Query(Criteria.where("_id").is(idUser));

        Update removeMeet = new Update().pull("settle.listMeet", new BasicDBObject("idMeet", idMeet));

        return mongoTemplate.findAndModify(query, removeMeet, UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomain);
    }

    /*------------------- BILL --------------------*/
    @Override
    public Mono<UserDomain> addBillListMeetSettle(final String idUser, final String idMeet, final BillDomain bill) {
        Query query = new Query(Criteria.where("_id").is(idUser))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet));

        Update addBillListMeet = new Update().addToSet("settle.listMeet.$[index].listBill", BillMapper.INSTANCE.convertDomainToEntity(bill))
                .filterArray(Criteria.where("index").is(idMeet));

        return mongoTemplate.findAndModify(query, addBillListMeet, UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomain)
                .switchIfEmpty(addBillToMeetListManualMode(idUser, idMeet, bill))
                .onErrorResume((error) -> {
                    log.error(">>>>> Error Add Bill: {}", error.getMessage());
                    return addBillToMeetListManualMode(idUser, idMeet, bill);
                });
    }

    private Mono<UserDomain> addBillToMeetListManualMode(final String idUser, final String idMeet, final BillDomain bill) {
        log.warn(">>>>> Manual action to Add Bill in Meet is activated.");
        Query query = new Query(Criteria.where("_id").is(idUser))
                .addCriteria(Criteria.where("settle.listMeet").elemMatch(Criteria.where("idMeet").is(idMeet)));

        return mongoTemplate.findOne(query, MeetEntity.class)
                .flatMap(meet -> {
                    if (Objects.isNull(meet.getListBill()) || meet.getListBill().size() == 0) {
                        meet.setListBill(new ArrayList<>());
                    }
                    meet.getListBill().add(BillMapper.INSTANCE.convertDomainToEntity(bill));
                    return updateMeetDB(idUser, meet).map(UserMapper.INSTANCE::convertEntityToDomain);
                })
                .onErrorResume((error) -> {
                    log.error(">>>>> Error finding Meet Manual: {}", error.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<UserDomain> updateBillListMeetSettle(String idUser, String idMeet, BillDomain bill) {
        Query query = new Query(Criteria.where("_id").is(idUser))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet))
                .addCriteria(Criteria.where("settle.listMeet.listBill.idBill").is(bill.getIdBill()));

        Update updateBillListMeet = new Update().set("settle.listMeet.$[index].listBill.$", BillMapper.INSTANCE.convertDomainToEntity(bill))
                .filterArray(Criteria.where("index").is(idMeet));

        return mongoTemplate.findAndModify(query, updateBillListMeet, UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomain);
    }

    @Override
    public Mono<UserDomain> removeBillListMeetSettle(String idUser, String idMeet, String idBill) {
        Query query = new Query(Criteria.where("_id").is(idUser))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet));

        Update removeBillListMeet = new Update().pull("settle.listMeet.$.listBill", new BasicDBObject("idBill", idBill));

        return mongoTemplate.findAndModify(query, removeBillListMeet, UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomain);
    }
}
