package by.kolesnik.springsecuritytms.facade;

import by.kolesnik.springsecuritytms.dto.LoginRequestDto;
import by.kolesnik.springsecuritytms.dto.RegisterRequestDto;
import by.kolesnik.springsecuritytms.dto.TokenResponseDto;
import by.kolesnik.springsecuritytms.service.AuthService;
import by.kolesnik.springsecuritytms.service.JwtService;
import by.kolesnik.springsecuritytms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

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

        userService.createUser(username, password);

        authService.login(username, password);

        return jwtService.generateToken(username);
    }
}
