package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;

@Repository
public interface IMeetRepository extends ReactiveMongoRepository<MeetEntity, String> {

    Flux<MeetEntity> findByIdMeet(String idMeet);

    @Query(value = "{'idMeet': ?0, 'idUser':?1 }")
    Mono<MeetEntity> findByIdMeetAndIdUser(String idMeet, String idUser);

    @Query(value = "{'idUser': ?0, 'active':?1 }")
    Flux<MeetEntity> findByIdUserAndActive(String idUser, Boolean active);

}
