package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.mongo.BillMapper;
import uy.com.hachebackend.settle.application.mapper.mongo.MeetMapper;
import uy.com.hachebackend.settle.application.mapper.mongo.UserMapper;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.domain.repository.ISettlePersist;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.BillEntity;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;


@Slf4j
@Component
@RequiredArgsConstructor
public class SettleRepositoryImpl implements ISettlePersist {

    private final IUserRepository userRepository;
    private final IMeetRepository meetRepository;
    private final IBillRepository billRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UserDomain> selectUserSettle(String idUser) {
        return userRepository.findByIdUser(idUser)
                .map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<UserDomain> saveUserSettle(UserDomain user) {
        return userRepository
                .save(UserMapper.INSTANCE.convertDomainToEntityMongo(user))
                .map(UserMapper.INSTANCE::convertEntityToDomainMongo);
    }

    public Flux<MeetDomain> selectAllMeetSettle(final String idUser, Boolean active) {
        return meetRepository.findByIdUserAndActive(idUser, active)
                .map(MeetMapper.INSTANCE::convertEntityToDomainMongo);
    }

    public Flux<MeetDomain> selectMeetSettle(final String idMeet) {
        return meetRepository.findByIdMeet(idMeet)
                .map(MeetMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<MeetDomain> saveMeetSettle(MeetDomain meet) {
        return meetRepository.save(MeetMapper.INSTANCE.convertDomainToEntityMongo(meet))
                .map(MeetMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<MeetDomain> updateMeetSettle(MeetDomain meet) {
        Query query = new Query(Criteria.where("idMeet").is(meet.getIdMeet())
                .andOperator(Criteria.where("idUser").is(meet.getIdUser())));
        Update update = new Update();

        update.set("name", meet.getName());
        update.set("active", meet.getActive());
        update.set("updated", meet.getUpdated());
        update.set("owner", meet.getOwner().getName());

        return mongoTemplate.findAndModify(query, update, MeetEntity.class).map(MeetMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<Void> removeMeetSettle(String idMeet, String idUser) {
        Query query = new Query(Criteria.where("idMeet").is(idMeet)
                .andOperator(Criteria.where("idUser").is(idUser)));
        return mongoTemplate.findAndRemove(query, MeetEntity.class).then();
    }

    /*------------------- BILL --------------------*/
    @Override
    public Mono<BillDomain> saveBillSettle(final BillDomain bill) {
        return billRepository.save(BillMapper.INSTANCE.convertDomainToEntityMongo(bill)).map(BillMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<Void> removeBillSettle(String idBill) {
        Query query = Query.query(Criteria.where("idBill").is(idBill));
        // Se utiliza .then(); para indicar que debe ejecutarse y no espera ningun resultado devuelto.
        return mongoTemplate.findAndRemove(query, BillEntity.class).then();
    }

    @Override
    public Flux<BillEntity> removeBillsByIdMeetSettle(final String idMeet) {
        Query query = Query.query(Criteria.where("idMeet").is(idMeet));
        // Se utiliza .then(); para indicar que debe ejecutarse y no espera ningun resultado.
        return mongoTemplate.findAllAndRemove(query, BillEntity.class);
    }

    @Override
    public Mono<MeetDomain> selectMeetByIdMeetAndIdUserSettle(final String idMeet, final String idUser) {
        return meetRepository.findByIdMeetAndIdUser(idMeet, idUser)
                .map(MeetMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Flux<BillDomain> selectBillByIdMeetSettle(final String idMeet) {
        return billRepository.findByIdMeet(idMeet).map(BillMapper.INSTANCE::convertEntityToDomainMongo);
    }

    @Override
    public Mono<BillDomain> selectBillByIdBillAndIdMeetSettle(final String idBill, final String idMeet) {
        return billRepository.findByIdBillAndIdMeet(idBill, idMeet).map(BillMapper.INSTANCE::convertEntityToDomainMongo);
    }


}
