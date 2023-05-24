package uy.com.hachebackend.settle.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class UserContextReactive implements ServerSecurityContextRepository {

    private static String URL_PERMIT_ALL;
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserContextReactive(@Value("${app.url.permit.all}") final String uri_permit) {
        this.URL_PERMIT_ALL = uri_permit;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();

        log.info("***** Uri consultada: {} - {} ", request.getMethod(), request.getPath().pathWithinApplication().value() /*, request.getHeaders().getOrigin()*/);

        // No verifica si se esta consultando sobre los endpoint permitidos.
        if (!this.URL_PERMIT_ALL.contains(request.getPath().pathWithinApplication().value())) {
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String authToken = null;
            if (Objects.nonNull(authHeader) && authHeader.startsWith(TOKEN_PREFIX)) {
                authToken = authHeader.replace(TOKEN_PREFIX, "");
            } else {
                log.warn(">>>>> Couldn't find Bearer string, will ignore the header.");
            }
            if (Objects.nonNull(authToken)) {

                log.info("***** con token.");

                Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
                return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
            }
        }
        return Mono.empty();
    }
}
