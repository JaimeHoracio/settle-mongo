package uy.com.hachebackend.settle.application.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.mapper.mongo.UserMapper;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.domain.repository.IUserPersist;
import uy.com.hachebackend.settle.infrastructure.dto.UserDto;
import uy.com.hachebackend.settle.security.authentication.JWTUtil;

import java.util.ArrayList;

import static uy.com.hachebackend.settle.domain.model.EnumRole.USER;

@Service
public class UserService {

    public Mono<UserDto> findUser(final String email, final IUserPersist userDomain) {
        return userDomain.findUser(email).map(UserMapper.INSTANCE::convertDomainToDto);
    }

    public Mono<UserDto> createUser(final String email, String name, final String password, final IUserPersist userDomain) {
        ArrayList roles = new ArrayList();
        roles.add(USER.name());
        return userDomain.createUser(email, name, JWTUtil.passwordEncoder().encode(password), roles)
                .map(u -> UserMapper.INSTANCE.convertDomainToDto((UserDomain) u));
    }
}
