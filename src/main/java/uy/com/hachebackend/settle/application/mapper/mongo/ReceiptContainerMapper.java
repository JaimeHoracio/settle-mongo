package uy.com.hachebackend.settle.application.mapper.mongo;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.ReceiptContainerDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.ReceiptContainerEntity;

@Mapper
public interface ReceiptContainerMapper {

    ReceiptContainerMapper INSTANCE = Mappers.getMapper(ReceiptContainerMapper.class);

    ReceiptContainerEntity convertDomainToEntityMongo(ReceiptContainerDomain entity);

}
