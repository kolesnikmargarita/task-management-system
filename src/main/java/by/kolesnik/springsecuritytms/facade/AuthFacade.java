package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.security.LoginRequestDto;
import by.kolesnik.springsecuritytms.dto.security.RegisterRequestDto;
import by.kolesnik.springsecuritytms.dto.security.TokenResponseDto;
import by.kolesnik.springsecuritytms.service.authorization.AuthService;
import by.kolesnik.springsecuritytms.service.authorization.JwtService;
import by.kolesnik.springsecuritytms.service.db.UserServiceDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final JwtService jwtService;
    private final AuthService authService;
    private final UserServiceDB userService;

    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginRequestDto dto) {
        final String username = dto.getUsername();
        final String password = dto.getPassword();

        authService.login(username, password);

        return jwtService.generateToken(username);
    }

    @Transactional
    public TokenResponseDto register(RegisterRequestDto dto) {
        final String username = dto.getUsername();
        final String password = dto.getPassword();
        final String name = dto.getName();

        userService.createUser(username, password, name);

        authService.login(username, password);

        return jwtService.generateToken(username);
    }
}
