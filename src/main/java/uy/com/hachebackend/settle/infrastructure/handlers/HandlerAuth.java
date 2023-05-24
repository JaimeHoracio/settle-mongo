package uy.com.hachebackend.settle.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.services.UserService;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.infrastructure.dto.ErrorDto;
import uy.com.hachebackend.settle.infrastructure.dto.UserDto;
import uy.com.hachebackend.settle.infrastructure.mongo.persistence.SettleRepositoryImpl;

@Component
@RequiredArgsConstructor
public class HandlerAuth {

    private final SettleRepositoryImpl mongoRepository;

    private final UserService userService;

    public Mono<ServerResponse> signUpSettle(final ServerRequest request) {
        return request.bodyToMono(UserDomain.class)
                .flatMap(user -> userService.findUser(user.getEmail(), mongoRepository)
                        .flatMap(userServer -> ServerResponse.badRequest()
                                .body(Mono.just(
                                                ErrorDto.builder()
                                                        .message("User already exist")
                                                        .codeError(0).build())
                                        , ErrorDto.class))
                        .switchIfEmpty(userService.saveUser(user.getEmail(),
                                        user.getName(),
                                        user.getPassword(),
                                        mongoRepository)
                                .flatMap(u -> ServerResponse.ok().body(Mono.just(u), UserDto.class))
                        ))
                .switchIfEmpty(ServerResponse.badRequest().body(
                        Mono.just(ErrorDto.builder()
                                .message("User is empty")
                                .codeError(0).build())
                        , ErrorDto.class));
    }

    public Mono<ServerResponse> loginSettle(final ServerRequest request) {
        return request.bodyToMono(UserDomain.class)
                .flatMap(
                        user -> userService.findUser(user.getEmail(), mongoRepository)
                                .flatMap(u -> ServerResponse.ok().body(Mono.just(u), UserDto.class))
                                .switchIfEmpty(ServerResponse.badRequest()
                                        .body(Mono.just(ErrorDto.builder()
                                                        .message("User does not exist")
                                                        .codeError(0).build())
                                                , ErrorDto.class)));
    }

    public Mono<ServerResponse> refreshSettle(final ServerRequest request) {
        return request.bodyToMono(UserDomain.class).flatMap(user -> ServerResponse.ok().body(Mono.just("Login"), String.class));
    }
}
