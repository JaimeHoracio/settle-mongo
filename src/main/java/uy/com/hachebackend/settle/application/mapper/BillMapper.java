package uy.com.hachebackend.settle.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.BillDomain;
import uy.com.hachebackend.settle.infrastructure.dto.BillDto;

@Mapper
public interface BillMapper {

    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    BillDomain convertDtoToDomain(final BillDto dto);

    BillDto convertDomainToEntity(final BillDomain dto);


}
