package uy.com.hachebackend.settle.domain.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.domain.model.CurrencyDomain;

public interface ICurrencyPersist {

    Mono<CurrencyDomain> saveCurrency(String code, String name, Integer num, String country);

    Flux<CurrencyDomain> getAllCurrency();
}
