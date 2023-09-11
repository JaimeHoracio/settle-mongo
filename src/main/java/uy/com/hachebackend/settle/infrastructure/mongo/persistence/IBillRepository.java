package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.BillEntity;

@Repository
public interface IBillRepository extends ReactiveMongoRepository<BillEntity, String> {

    Mono<BillEntity> findByIdBill(String idBill);

    Flux<BillEntity> findByIdMeet(String idMeet);

    Mono<BillEntity> findByIdBillAndIdMeet(String idBill, String idMeet);

    Mono<BillEntity> deleteByIdMeet(String idMeet);
}
