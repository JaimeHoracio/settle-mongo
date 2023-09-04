package uy.com.hachebackend.settle.application.mapper.mongo;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.hachebackend.settle.domain.model.PaymentContainerDomain;
import uy.com.hachebackend.settle.domain.model.ReceiptContainerDomain;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.PaymentContainerEntity;
import uy.com.hachebackend.settle.infrastructure.mongo.entity.ReceiptContainerEntity;

import java.util.List;

@Mapper
public interface PaymentContainerMapper {

    PaymentContainerMapper INSTANCE = Mappers.getMapper(PaymentContainerMapper.class);

    PaymentContainerEntity convertDomainToEntityMongo(PaymentContainerDomain entity);
    List<PaymentContainerEntity> convertDomainToEntityMongo(List<PaymentContainerDomain> list);
}
