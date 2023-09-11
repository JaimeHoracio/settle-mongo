package uy.com.hachebackend.settle.infrastructure.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import uy.com.hachebackend.settle.infrastructure.handlers.HandlerSettle;

@Configuration
public class SettleRouter {


    /**
     * FILTRO PARA CONTROLAR QUE EL USUARIO QUE HACE LA MODIFICACION SEA EL MISMO DEL TOKEN!!!!
     */


    @Bean
    public RouterFunction<ServerResponse> initSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().GET("/api/settle/init",
                handlerSettle::initSettle).build();
    }

    // Meet
    @Bean
    public RouterFunction<ServerResponse> selectAllMeetActiveSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().GET("/api/settle/meet/all/active",
                RequestPredicates.queryParam("idUser", t -> true),
                request -> handlerSettle.selectAllMeetSettle(request, true)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> selectAllMeetNotActiveSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().GET("/api/settle/meet/all/notactive",
                RequestPredicates.queryParam("idUser", t -> true),
                request -> handlerSettle.selectAllMeetSettle(request, false)).build();
    }


    @Bean
    public RouterFunction<ServerResponse> selectMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().GET("/api/settle/meet/select",
                RequestPredicates.queryParam("idMeet", t -> true),
                handlerSettle::selectMeetSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> addMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().POST("/api/settle/meet",
                handlerSettle::addMeetSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> updateMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().PUT("/api/settle/meet",
                handlerSettle::updateMeetSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> closeMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().POST("/api/settle/meet/close",
                handlerSettle::closeMeetSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> removeMeetSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().DELETE("/api/settle/meet",
                handlerSettle::removeMeetSettle).build();
    }

    // Bill
    @Bean
    public RouterFunction<ServerResponse> addBillSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().POST("/api/settle/bill",
                handlerSettle::addBillSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> updateBillSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().PUT("/api/settle/bill",
                handlerSettle::updateBillSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> removeBillSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().DELETE("/api/settle/bill",
                handlerSettle::removeBillSettle).build();
    }

    @Bean
    public RouterFunction<ServerResponse> selectAllBillSettle(HandlerSettle handlerSettle) {
        return RouterFunctions.route().GET("/api/settle/bill/all",
                RequestPredicates.queryParam("idMeet", t -> true),
                handlerSettle::selectAllBillSettle).build();
    }

}
