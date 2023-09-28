package uy.com.hachebackend.settle.application.mapper.mongo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.MeetDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.MeetEntity;
import uy.com.hachebackend.settle.infrastructure.dto.MeetDto;

@Mapper
public interface MeetMapper {

    MeetMapper INSTANCE = Mappers.getMapper(MeetMapper.class);

    MeetDomain convertDtoToDomainMongo(final MeetDto meetDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "owner.name", target = "owner")
    MeetEntity convertDomainToEntityMongo(final MeetDomain meetDomain);

    @Mapping(source = "owner", target = "owner.name")
    MeetDomain convertEntityToDomainMongo(final MeetEntity meetEntity);

}
