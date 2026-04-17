package by.kolesnik.springsecuritytms.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AuthUtils {

    public static final String PREFIX = "Bearer ";
    // Authorization: Bearer ...

    public String getAuthTokenFromRequestHeader(HttpServletRequest request) {
        final String bearerString = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(bearerString != null && !bearerString.isBlank() && bearerString.startsWith(PREFIX)) {
            return bearerString.substring(PREFIX.length());
        }

        log.warn("В запросе по пути {} [{}] не был найден Bearer-токен", request.getMethod(), request.getRequestURI());
        return null;
    }
}
