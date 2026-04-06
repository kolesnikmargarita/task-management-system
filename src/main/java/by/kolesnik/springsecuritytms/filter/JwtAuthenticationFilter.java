package by.kolesnik.springsecuritytms.filter;

import by.kolesnik.springsecuritytms.util.AuthUtils;
import by.kolesnik.springsecuritytms.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthUtils authUtils;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        // прочитать токен из заголовка
        final String token = authUtils.getAuthTokenFromRequestHeader(request);

        // если токен есть, провалидировать и установить пользователя в SecurityContextHolder
        if(token != null && !token.isBlank()) {
            performAuthentication(request, token);
        }
        
        filterChain.doFilter(request, response);
    }

    private void performAuthentication(HttpServletRequest request, String token) {
        final Claims claims = jwtUtils.parseToken(token);
        final String username = claims.getSubject();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

        final WebAuthenticationDetails authenticationDetails =
                new WebAuthenticationDetailsSource().buildDetails(request);

        authenticationToken.setDetails(authenticationDetails);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
