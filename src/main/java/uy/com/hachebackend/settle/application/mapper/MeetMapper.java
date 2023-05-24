package uy.com.hachebackend.settle.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;
import uy.com.hachebackend.settle.infrastructure.dto.MeetDto;

@Mapper
public interface MeetMapper {

    MeetMapper INSTANCE = Mappers.getMapper(MeetMapper.class);

    MeetDomain convertDtoToDomain(final MeetDto meetDto);

    MeetEntity convertDomainToEntity(final MeetDomain meetDomain);

}
