package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.controller.openapi.UserOpenApi;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserUpdateDto;
import by.kolesnik.springsecuritytms.enums.CacheMode;
import by.kolesnik.springsecuritytms.facade.UserFacade;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserOpenApi {

    private final UserFacade userFacade;

    @GetMapping
    @Override
    public List<UserGetBasicDto> findAll(@RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return userFacade.findAll(cacheMode);
    }

    @GetMapping("/{id}")
    @Override
    public UserGetDto findById(@PathVariable Long id, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return userFacade.findById(id, cacheMode);
    }

    @PatchMapping("/{id}")
    @Override
    public UserGetBasicDto update(@PathVariable Long id, @RequestBody UserUpdateDto dto, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return userFacade.update(id, dto, cacheMode);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        userFacade.delete(id, cacheMode);
    }
}
