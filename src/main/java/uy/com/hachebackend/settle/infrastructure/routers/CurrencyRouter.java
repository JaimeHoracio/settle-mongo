package uy.com.hachebackend.settle.infrastructure.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import uy.com.hachebackend.settle.infrastructure.handlers.HandlerCurrency;

@Configuration
public class CurrencyRouter {

    @Bean
    public RouterFunction<ServerResponse> getCurrencyAll(HandlerCurrency handlerCurrency) {
        return RouterFunctions.route().GET("/api/hache/currency/all", handlerCurrency::getAllCurrency).build();
    }
}
