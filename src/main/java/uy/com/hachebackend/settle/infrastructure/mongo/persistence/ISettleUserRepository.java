package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.UserEntity;

@Repository
public interface ISettleUserRepository extends ReactiveMongoRepository<UserEntity, String> {

    Mono<UserEntity> findById(final String email);

    // No tiene sentido filtrar por meet porque te devuelve el usuario entero y luego hay que buscarlo nuevamente.
    @Query(value = "{'_id': ?0, 'settle.listMeet.idMeet':?1 }")
    Mono<UserEntity> findByIdUserIdMeet(final String email, final String idMeet);

}
