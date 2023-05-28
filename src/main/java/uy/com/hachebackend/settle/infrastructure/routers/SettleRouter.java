package uy.com.hachebackend.settle.infrastructure.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import uy.com.hachebackend.settle.infrastructure.handlers.HandlerSettle;

@Configuration
public class SettleRouter {

    @Bean
    public RouterFunction<ServerResponse> initSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().GET("/api/hache/settle/init", handlerSettle::initSettle).build();
    }
    // Meet
    @Bean
    public RouterFunction<ServerResponse> addMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().POST("/api/hache/settle/meet", handlerSettle::addMeetSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> updateMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().PUT("/api/hache/settle/meet", handlerSettle::updateMeetSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> closeMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().POST("/api/hache/settle/meet/close", handlerSettle::closeMeetSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> removeMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().DELETE("/api/hache/settle/meet", handlerSettle::removeMeetSettle).build();
    }

    // Bill
    @Bean
    public RouterFunction<ServerResponse> addBillSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().POST("/api/hache/settle/bill", handlerSettle::addBillSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> updateBillSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().PUT("/api/hache/settle/bill", handlerSettle::updateBillSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> removeBillSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().DELETE("/api/hache/settle/bill", handlerSettle::removeBillSettle).build();
    }

}
