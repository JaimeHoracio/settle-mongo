package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.mongo.BillMapper;
import uy.com.hachebackend.settle.application.mapper.mongo.MeetMapper;
import uy.com.hachebackend.settle.application.mapper.mongo.UserMapper;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.domain.repository.IUserPersist;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.UserEntity;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SettleRepositoryImpl implements IUserPersist {

    private final ISettleUserRepository userRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UserDomain> findUser(String email) {
        return userRepository.findById(email)
                .map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<UserDomain> createUser(String email, String name, String password, List<String> roles) {
        UserEntity user = UserEntity.builder().email(email).name(name).password(password).roles(roles).build();
        return userRepository.save(user).map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    public Mono<MeetDomain> selectMeetSettle(final String idMeet) {
        Query query = new Query(Criteria.where("settle.listMeet.idMeet").is(idMeet));

        return mongoTemplate.findOne(query, MeetEntity.class).map(MeetMapper.INSTANCE::convertEntityToDomainMongo)
                .onErrorResume((error) -> {
                    log.error(">>>>> Error finding Meet Select: {}", error.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<UserDomain> addMeetSettle(String email, MeetDomain meet) {
        //Reseteo la lista de pagos porque lo manejo aparte
        meet.setListBill(new ArrayList<>());
        Query query = new Query(Criteria.where("_id").is(email));
        Update addMeet = new Update().addToSet("settle.listMeet", MeetMapper.INSTANCE.convertDomainToEntityMongo(meet));

        return mongoTemplate.findAndModify(query, addMeet, FindAndModifyOptions.options().returnNew(true),
                UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<UserDomain> updateMeetSettle(final String email, final MeetDomain meet) {
        return updateMeetDB(email, MeetMapper.INSTANCE.convertDomainToEntityMongo(meet)).map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    public Mono<UserEntity> updateMeetDB(final String email, final MeetEntity meet) {
        Query query = new Query(Criteria.where("_id").is(email))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(meet.getIdMeet()));

        Update updateMeet = new Update().set("settle.listMeet.$", meet);

        return mongoTemplate.findAndModify(query, updateMeet, FindAndModifyOptions.options().returnNew(true), UserEntity.class);
    }

    @Override
    public Mono<UserDomain> closeMeetSettle(String email, String idMeet) {
        Query query = new Query(Criteria.where("_id").is(email))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet));

        Update closeMeet = new Update().set("settle.listMeet.$.active", false).set("settle.listMeet.$.updated", new Date());

        return mongoTemplate.findAndModify(query, closeMeet, FindAndModifyOptions.options().returnNew(true),
                UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<UserDomain> removeMeetSettle(String email, String idMeet) {
        Query query = new Query(Criteria.where("_id").is(email));

        Update removeMeet = new Update().pull("settle.listMeet", new BasicDBObject("idMeet", idMeet));

        return mongoTemplate.findAndModify(query, removeMeet, FindAndModifyOptions.options().returnNew(true),
                UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    /*------------------- BILL --------------------*/
    @Override
    public Mono<UserDomain> addBillListMeetSettle(final String email, final String idMeet, final BillDomain bill) {
        Query query = new Query(Criteria.where("_id").is(email))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet));

        Update addBillListMeet = new Update().addToSet("settle.listMeet.$.listBill", BillMapper.INSTANCE.convertDomainToEntityMongo(bill));

        return mongoTemplate.updateFirst(query, addBillListMeet, UserEntity.class)
                .flatMap(result -> {
                    if (result.getModifiedCount() > 0) {
                        return Mono.just(UserDomain.builder().email(email).build());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Mono<UserDomain> updateBillListMeetSettle(String email, String idMeet, BillDomain bill) {

        return findUser(email)
                .flatMap(user -> {
                            Optional<MeetDomain> me = getMeetById(user, idMeet);
                            if (me.isPresent()) {
                                MeetDomain meet = me.get();
                                if (Objects.nonNull(meet.getListBill()) && meet.getListBill().size() > 0) {
                                    meet.setListBill(meet.getListBill().stream().parallel()
                                            .map(b -> b.getIdBill().equals(bill.getIdBill()) ? bill : b)
                                            .collect(Collectors.toList()));
                                    return updateMeetSettle(email, meet);
                                }
                            }
                            return Mono.empty();
                        }
                );

        /*
        NO LOGRÉ QUE FUNCIONARA MODIFICAR UN ELEMENTO DE DOS LISTAS ANIDADAS

        Query query = new Query(Criteria.where("_id").is(email))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet))
                .addCriteria(Criteria.where("settle.listMeet.listBill.idBill").is(bill.getIdBill()));

        Update updateBillListMeet = new Update().set("settle.listMeet.$[meet].listBill.$[bill]", BillMapper.INSTANCE.convertDomainToEntityMongo(bill))
                .filterArray(Criteria.where("meet").is(idMeet))
                .filterArray(Criteria.where("bill").is(bill.getIdBill()));

        return mongoTemplate.findAndModify(query, updateBillListMeet, FindAndModifyOptions.options().returnNew(true),
                UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomainMongo);

         */
    }

    @Override
    public Mono<UserDomain> removeBillListMeetSettle(String email, String idMeet, String idBill) {
        Query query = new Query(Criteria.where("_id").is(email))
                .addCriteria(Criteria.where("settle.listMeet.idMeet").is(idMeet));

        Update removeBillListMeet = new Update().pull("settle.listMeet.$.listBill", new BasicDBObject("idBill", idBill));

        return mongoTemplate.findAndModify(query, removeBillListMeet, FindAndModifyOptions.options().returnNew(true),
                UserEntity.class).map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    private Optional<MeetDomain> getMeetById(final UserDomain user, final String idMeet) {
        return user.getSettle().getListMeet().stream().parallel()
                .filter(m -> m.getIdMeet().equals(idMeet)).findAny();
    }

}
