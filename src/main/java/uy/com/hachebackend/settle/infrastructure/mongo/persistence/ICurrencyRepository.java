package uy.com.hachebackend.settle.infrastructure.mongo.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.CurrencyISOEntity;

@Repository
public interface ICurrencyRepository extends ReactiveMongoRepository<CurrencyISOEntity, String> {
}
