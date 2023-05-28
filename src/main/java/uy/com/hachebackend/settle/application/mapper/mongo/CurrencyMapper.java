package uy.com.hachebackend.settle.application.mapper.mongo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.CurrencyDomain;
import uy.com.hachebackend.settle.infrastructure.dto.CurrencyDto;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.CurrencyISOEntity;

@Mapper
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    //@Mapping(source = "codeISO", target = "code")
    //@Mapping(source = "numISO", target = "num")
    CurrencyDomain convertEntityToDomainMongo(final CurrencyISOEntity currencyDomain);

    CurrencyDto convertDomainToDto(final CurrencyDomain currencyDomain);

}
