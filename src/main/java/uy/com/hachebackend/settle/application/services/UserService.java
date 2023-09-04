package uy.com.hachebackend.settle.application.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.mongo.UserMapper;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.domain.repository.IUserPersist;
import uy.com.hachebackend.settle.infrastructure.dto.UserDto;
import uy.com.hachebackend.settle.security.authentication.HashingEncode;
import uy.com.hachebackend.settle.security.authentication.JWTUtil;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static uy.com.hachebackend.settle.domain.model.EnumRole.USER;

@Service
public class UserService {

    public Mono<UserDto> findUser(final String idUser, final IUserPersist userPersist) {
        String hashIdUser;
        try {
            hashIdUser = HashingEncode.hashMessage(idUser);
        } catch (NoSuchAlgorithmException ex) {
            return Mono.error(ex.getCause());
        }
        return userPersist.findUser(hashIdUser).map(UserMapper.INSTANCE::convertDomainToDto);
    }

    public Mono<UserDto> createUser(final String idUser, String name, final String password, Boolean guest, final IUserPersist userPersist) {
        ArrayList roles = new ArrayList();
        roles.add(USER.name());
        String hashIdUser;
        try {
            hashIdUser = HashingEncode.hashMessage(idUser);
        } catch (NoSuchAlgorithmException ex) {
            return Mono.error(ex.getCause());
        }
        return userPersist.createUser(hashIdUser, name, JWTUtil.passwordEncoder().encode(password), guest, roles)
                .map(u -> UserMapper.INSTANCE.convertDomainToDto((UserDomain) u));
    }
}
