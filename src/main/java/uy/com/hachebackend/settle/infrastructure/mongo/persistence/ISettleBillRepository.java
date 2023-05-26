package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.BillEntity;

@Repository
public interface ISettleBillRepository extends ReactiveMongoRepository<BillEntity, String> {

    Mono<BillEntity> findById(final String idBill);

}
