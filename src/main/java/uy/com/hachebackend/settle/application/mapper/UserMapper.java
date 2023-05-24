package uy.com.hachebackend.settle.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.UserEntity;
import uy.com.hachebackend.settle.infrastructure.dto.UserDto;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //@Mapping(target = "idUser", ignore = true)
    UserEntity convertDomainToEntity(final UserDomain domain);

    UserDomain convertEntityToDomain(final UserEntity entity);

    UserDto convertDomainToDto(final UserDomain domain);

}
