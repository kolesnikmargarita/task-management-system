package by.kolesnik.springsecuritytms.controller;

import by.kolesnik.springsecuritytms.controller.openapi.GroupOpenApi;
import by.kolesnik.springsecuritytms.dto.group.*;
import by.kolesnik.springsecuritytms.enums.CacheMode;
import by.kolesnik.springsecuritytms.facade.GroupFacade;
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
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController implements GroupOpenApi {

    private final GroupFacade groupFacade;

    @GetMapping
    @Override
    public List<GroupGetBasicDto> findAll(@RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return groupFacade.findAll(cacheMode);
    }

    @GetMapping("/{id}")
    @Override
    public GroupGetDto findById(@PathVariable Long id, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return groupFacade.findById(id, cacheMode);
    }

    @PostMapping
    @Override
    public GroupGetBasicDto create(@RequestBody GroupCreateDto dto, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return groupFacade.create(dto, cacheMode);
    }

    @PatchMapping("/{id}")
    @Override
    public GroupGetBasicDto update(@PathVariable Long id, @RequestBody GroupUpdateDto dto, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return groupFacade.update(id, dto, cacheMode);
    }

    @PatchMapping("/{id}/add_user")
    @Override
    public GroupGetDto addUser(@PathVariable Long id, @RequestBody GroupUserAddDto userAddDto, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        return groupFacade.addUser(id, userAddDto, cacheMode);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id, @RequestParam(value = "cacheMode", defaultValue = "NONE_CACHE") CacheMode cacheMode) {
        groupFacade.delete(id, cacheMode);
    }
}
