package uy.com.hachebackend.settle.application.mapper.mongo;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.infrastructure.dto.BillDto;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.BillEntity;

@Mapper
public interface BillMapper {

    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    BillDomain convertDtoToDomainMongo(final BillDto dto);

    BillEntity convertDomainToEntityMongo(final BillDomain dto);

}
