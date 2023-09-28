package uy.com.hachebackend.settle.application.mapper.mongo;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.UserEntity;
import uy.com.hachebackend.settle.infrastructure.dto.UserDto;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //@Mapping(target = "email", ignore = true)
    UserDomain convertEntityToDomainMongo(final UserEntity entity);

    UserEntity convertDomainToEntityMongo(final UserDomain entity);

    UserDto convertDomainToDto(final UserDomain domain);

}
