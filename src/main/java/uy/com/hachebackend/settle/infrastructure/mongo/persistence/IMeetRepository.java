package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;

@Repository
public interface IMeetRepository extends ReactiveMongoRepository<MeetEntity, String> {

    Flux<MeetEntity> findByIdMeet(String idMeet);

    Mono<MeetEntity> findByIdUserAndIdMeet(String idUser, String idMeet);

    Flux<MeetEntity> findByIdUserAndActive(String idUser, Boolean active);

}
