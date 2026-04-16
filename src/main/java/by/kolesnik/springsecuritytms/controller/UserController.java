package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.controller.openapi.UserOpenApi;
import by.kolesnik.springsecuritytms.dto.ErrorResponse;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserUpdateDto;
import by.kolesnik.springsecuritytms.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserOpenApi {

    private final UserFacade userFacade;

    @GetMapping
    @Override
    public List<UserGetBasicDto> findAll() {
        return userFacade.findAll();
    }

    @GetMapping("/{id}")
    @Override
    public UserGetDto findById(@PathVariable Long id) {
        return userFacade.findById(id);
    }

    @PatchMapping("/{id}")
    @Override
    public UserGetBasicDto update(@PathVariable Long id, @RequestBody UserUpdateDto dto) {
        return userFacade.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) {
        userFacade.delete(id);
    }
}
