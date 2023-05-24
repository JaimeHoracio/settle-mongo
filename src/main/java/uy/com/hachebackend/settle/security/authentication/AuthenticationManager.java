package uy.com.hachebackend.settle.security.authentication;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String authToken = authentication.getCredentials().toString();
        final Claims claims = JWTUtil.getClaims(authToken);
        if (Objects.nonNull(claims)) {
            String username;
            try {
                username = JWTUtil.getEmail(claims);
            } catch (Exception e) {
                username = null;
            }
            if (username != null && JWTUtil.isValid(claims)) {
                final List<String> roles = JWTUtil.getRoles(claims);
                final List authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
                final UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, username, authorities);
                //Agrego el usuario autenticado al contexto.
                SecurityContextHolder.getContext().setAuthentication(auth);
                return Mono.just(auth);
            }
        }
        return Mono.empty();
    }
}
