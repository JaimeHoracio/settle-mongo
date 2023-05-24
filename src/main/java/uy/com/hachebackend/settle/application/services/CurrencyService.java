package uy.com.hachebackend.settle.application.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.CurrencyMapper;
import uy.com.hachebackend.settle.domain.repository.ICurrencyPersist;
import uy.com.hachebackend.settle.infrastructure.dto.CurrencyDto;

@Service
public class CurrencyService {

    public Mono<CurrencyDto> saveCurrency(final String code, final String name, final Integer num,
                                          final String country, final ICurrencyPersist currencyRepository) {
        return currencyRepository.saveCurrency(code, name, num, country)
                .map(CurrencyMapper.INSTANCE::convertDomainToDto);
    }

    public Flux<CurrencyDto> getAllCurrency(final ICurrencyPersist currencyRepository) {
        return currencyRepository.getAllCurrency().map(CurrencyMapper.INSTANCE::convertDomainToDto);
    }

}
