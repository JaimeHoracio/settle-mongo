package uy.com.hachebackend.settle.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class JWTUtil {

    private static Integer EXP_TIME_SECOND;
    private static String SECRET_KEY;
    private static final String AUTHORITIES_KEY = "roles";
    private static final String NAME_KEY = "name";
    private static final String PWD_KEY = "password";

    public JWTUtil(@Value("${app.security.jwt.token.second_exp:}") final Integer time_exp,
                   @Value("${app.security.jwt.secret.key}") final String secret) {
        EXP_TIME_SECOND = time_exp;
        SECRET_KEY = secret;
    }

    public static String generateToken(final String email, final String name, final String password, final List<String> roles) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(EXP_TIME_SECOND); // expires in 15 minutes
        Date expDate = Date.from(expiration);

        return Jwts.builder()
                .setSubject(email)
                .claim(NAME_KEY, name)
                .claim(PWD_KEY, passwordEncoder().encode(password))
                .claim(AUTHORITIES_KEY, roles)
                .setIssuedAt(Date.from(now))
                .setExpiration(expDate)
                .signWith(createSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims getClaims(final String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(createSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (JwtException e) {
            log.error(">>>> Token error : " + e.getMessage());
            return null;
        }
    }

    private static SecretKey createSecretKey() {
        byte[] secretKeyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public static String getEmail(final Claims claims) {
        return claims.getSubject();
    }

    public static ArrayList getRoles(final Claims claims) {
        return claims.get(AUTHORITIES_KEY, ArrayList.class);
    }

    public static boolean isValid(final Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.after(new Date());
    }

    public static boolean matchesPassword(final String pass1, final String pass2) {
        return passwordEncoder().matches(pass1, pass2);
    }

    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
