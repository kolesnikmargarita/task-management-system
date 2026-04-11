package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.dto.security.TokenResponseDto;
import by.kolesnik.springsecuritytms.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtils jwtUtils;

    public TokenResponseDto generateToken(String username) {
        final Date naw = new Date();
        final Date expirationDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));

        final String token = jwtUtils.generateToken(username, naw, expirationDate);
        return new TokenResponseDto(token);
    }
}
