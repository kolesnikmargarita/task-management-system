package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.controller.openapi.AuthOpenApi;
import by.kolesnik.springsecuritytms.dto.security.LoginRequestDto;
import by.kolesnik.springsecuritytms.dto.security.RegisterRequestDto;
import by.kolesnik.springsecuritytms.dto.security.TokenResponseDto;
import by.kolesnik.springsecuritytms.facade.AuthFacade;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthOpenApi {

    private final AuthFacade authFacade;

    @PostMapping("/login")
    @Override
    public TokenResponseDto login(@RequestBody LoginRequestDto dto) {
        return authFacade.login(dto);
    }

    @PostMapping("/registration")
    @Override
    public TokenResponseDto register(@RequestBody RegisterRequestDto dto) {
        return authFacade.register(dto);
    }
}
