package uy.com.hachebackend.settle.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import uy.com.hachebackend.settle.application.services.CurrencyService;
import uy.com.hachebackend.settle.infrastructure.mongo.persistence.CurrencyRepositoryImpl;

import static uy.com.hachebackend.settle.infrastructure.handlers.HandlerUtils.createErrorResponse;
import static uy.com.hachebackend.settle.infrastructure.handlers.HandlerUtils.createSuccessResponse;

@Component
@RequiredArgsConstructor
public class HandlerCurrency {

    private final CurrencyRepositoryImpl currencyRepository;
    private final CurrencyService currencyService;

    public Mono<ServerResponse> saveCurrencyIso(final String code, final String name, final Integer num, final String country) {
        return currencyService.saveCurrency(code, name, num, country, currencyRepository)
                .flatMap(currency -> createSuccessResponse(currency))
                .onErrorResume((error) -> createErrorResponse("Error al guardar la moneda" + error.getMessage()));
    }

    public Mono<ServerResponse> getAllCurrency(ServerRequest request) {
        return currencyService.getAllCurrency(currencyRepository)
                .collectList()
                .flatMap(currency -> createSuccessResponse(currency))
                .onErrorResume((error) -> createErrorResponse("Error al obtener la moneda" + error.getMessage()));
    }
}
