package uy.com.hachebackend.settle.infrastructure.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import uy.com.hachebackend.settle.infrastructure.handlers.HandlerAuth;

@Configuration
public class AuthRouter {

   @Bean
   public RouterFunction<ServerResponse> registerSettle(HandlerAuth handlerAuth) {
      return RouterFunctions.route().POST("/api/settle/auth/signup", handlerAuth::signUpSettle).build();
   }

   @Bean
   public RouterFunction<ServerResponse> loginSettle(HandlerAuth handlerAuth) {
      return RouterFunctions.route().POST("/api/settle/auth/signin", handlerAuth::loginSettle).build();
   }

   @Bean
   public RouterFunction<ServerResponse> refreshLoginSettle(HandlerAuth handlerAuth) {
      return RouterFunctions.route().POST("/api/settle/settle/refresh", handlerAuth::refreshSettle).build();
   }

}
