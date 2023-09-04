package uy.com.hachebackend.settle.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.services.UserService;
import uy.com.hachebackend.settle.domain.model.UserDomain;
import uy.com.hachebackend.settle.infrastructure.dto.UserDto;
import uy.com.hachebackend.settle.infrastructure.mongo.persistence.SettleRepositoryImpl;
import uy.com.hachebackend.settle.security.authentication.JWTUtil;

import static uy.com.hachebackend.settle.infrastructure.handlers.HandlerUtils.createErrorResponse;
import static uy.com.hachebackend.settle.infrastructure.handlers.HandlerUtils.createSuccessResponse;
import static uy.com.hachebackend.settle.security.authentication.JWTUtil.matchesPassword;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandlerAuth {

    private final SettleRepositoryImpl mongoRepository;

    private final UserService userService;

    public Mono<ServerResponse> signUpSettle(final ServerRequest request) {
        System.out.println("En signUP");
        return request.bodyToMono(UserDomain.class)
                .flatMap(user -> {
                            String name = user.getName();

                            if (Strings.isEmpty(name)) {
                                return createErrorResponse("El nombre es obligatorio.");
                            } else {
                                return userService.findUser(user.getIdUser(), mongoRepository)
                                        .flatMap(userServer -> createErrorResponse("Usuario ya existe."))
                                        .switchIfEmpty(Mono.defer(() -> {
                                                    Mono<UserDto> userCreated = userService.createUser(user.getIdUser(),
                                                            user.getName(),
                                                            user.getPassword(),
                                                            user.getGuest(),
                                                            mongoRepository);

                                                    return userCreated.flatMap(u -> {
                                                        String token = JWTUtil.generateToken(u.getIdUser(),
                                                                u.getName(),
                                                                u.getPassword(),
                                                                u.getRoles());
                                                        u.setToken(token);
                                                        return createSuccessResponse(u);
                                                    });
                                                })
                                        )
                                        .onErrorResume((error) -> {
                                            log.error(">>>>> Error: {}", error.getMessage());
                                            return createErrorResponse(error.getMessage());
                                        });
                            }
                        }
                )
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> loginSettle(final ServerRequest request) {
        System.out.println("En signIN");
        return request.bodyToMono(UserDto.class)
                .flatMap(
                        user -> userService.findUser(user.getIdUser(), mongoRepository)
                                .flatMap(u -> {
                                    if (matchesPassword(user.getPassword(), u.getPassword())) {
                                        String token = JWTUtil.generateToken(u.getIdUser(),
                                                u.getName(),
                                                u.getPassword(),
                                                u.getRoles());
                                        u.setToken(token);
                                        return createSuccessResponse(u);
                                    } else {
                                        return createErrorResponse("Invalid credencial.");
                                    }
                                })
                                .switchIfEmpty(Mono.defer(() -> createErrorResponse("Usuario no existe.")))
                                .onErrorResume((error) -> {
                                    log.error(">>>>> Error: {}", error.getMessage());
                                    return createErrorResponse(error.getMessage());
                                })
                )
                .onErrorResume((error) -> {
                    log.error(">>>>> Error: {}", error.getMessage());
                    return createErrorResponse(error.getMessage());
                });
    }

    public Mono<ServerResponse> refreshSettle(final ServerRequest request) {
        return request.bodyToMono(UserDomain.class).flatMap(user -> ServerResponse.ok().body(Mono.just("Login"), String.class));
    }


}
