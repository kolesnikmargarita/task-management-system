package by.kolesnik.springsecuritytms.util;

import by.kolesnik.springsecuritytms.config.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public String generateToken(String username, Date naw, Date expirationDate) {
        final Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        return Jwts.builder()
                .subject(username)
                .issuedAt(naw)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token) {
        //final Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        final SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        /*return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseEncryptedClaims(token)
                .getBody();*/
        return Jwts.parser()
                .verifyWith(key) // Новое название для setSigningKey
                .build()
                .parseSignedClaims(token) // Используйте parseSignedClaims для подписанных JWT
                .getPayload();
    }
}
