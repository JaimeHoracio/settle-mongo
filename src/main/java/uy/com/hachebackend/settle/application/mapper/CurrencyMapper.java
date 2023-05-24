package uy.com.hachebackend.settle.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.CurrencyDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.CurrencyISOEntity;
import uy.com.hachebackend.settle.infrastructure.dto.CurrencyDto;

@Mapper
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    @Mapping(source = "currencyDomain.codeISO", target = "code")
    @Mapping(source = "currencyDomain.numISO", target = "num")
    CurrencyDomain convertEntityToDomain(final CurrencyISOEntity currencyDomain);

    CurrencyDto convertDomainToDto(final CurrencyDomain currencyDomain);

}
