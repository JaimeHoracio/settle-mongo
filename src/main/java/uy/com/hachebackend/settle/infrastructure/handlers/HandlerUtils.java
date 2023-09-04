package uy.com.hachebackend.settle.infrastructure.handlers;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.infrastructure.dto.ErrorDto;

public class HandlerUtils {

    public static Mono<ServerResponse> createErrorResponse(final String msg) {
        return ServerResponse
                .badRequest()
                .body(Mono.just(ErrorDto.builder()
                                .message(msg)
                                .codeError(0).build())
                        , ErrorDto.class);
    }

    public static <T> Mono<ServerResponse> createSuccessResponse(final T msg) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(msg), msg.getClass());
    }

}
