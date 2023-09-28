package uy.com.hachebackend.settle.application.mapper.mongo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.infrastructure.dto.BillDto;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.BillEntity;

import java.util.List;

@Mapper
public interface BillMapper {

    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    BillDomain convertDtoToDomainMongo(final BillDto dto);

    List<BillDomain> convertListDtoToDomainMongo(final List<BillDto> dto);

    @Mapping(source = "owner", target = "owner.name")
    BillDomain convertEntityToDomainMongo(final BillEntity dto);

    @Mapping(source = "owner.name", target = "owner")
    BillEntity convertDomainToEntityMongo(final BillDomain dto);

}
