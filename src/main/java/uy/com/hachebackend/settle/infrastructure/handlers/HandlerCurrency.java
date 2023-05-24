package uy.com.hachebackend.settle.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.services.CurrencyService;
import uy.com.hachebackend.settle.infrastructure.mongo.persistence.CurrencyRepositoryImpl;
import uy.com.hachebackend.settle.infrastructure.dto.CurrencyDto;
import uy.com.hachebackend.settle.infrastructure.dto.ErrorDto;

@Component
@RequiredArgsConstructor
public class HandlerCurrency {

    private final CurrencyRepositoryImpl currencyRepository;
    private final CurrencyService currencyService;

    public Mono<ServerResponse> saveCurrencyIso(final String code, final String name, final Integer num, final String country) {
        return currencyService.saveCurrency(code, name, num, country, currencyRepository)
                .flatMap(currency -> ServerResponse.ok().body(Mono.just(currency), CurrencyDto.class))
                .onErrorResume((error) ->
                        ServerResponse.badRequest()
                                .body(Mono.just(
                                        ErrorDto.builder()
                                                .message("Error al obtener la moneda" + error.getMessage())
                                                .codeError(0)
                                                .build()), ErrorDto.class));
    }

    public Mono<ServerResponse> getAllCurrency(ServerRequest request) {
        return currencyService.getAllCurrency(currencyRepository)
                .collectList()
                .flatMap(currency -> ServerResponse.ok().body(Mono.just(currency), CurrencyDto.class))
                .onErrorResume((error) ->
                        ServerResponse.badRequest()
                                .body(Mono.just(
                                        ErrorDto.builder()
                                                .message("Error al obtener la moneda: " + error.getMessage())
                                                .codeError(0)
                                                .build()), ErrorDto.class));
    }
}
