package by.kolesnik.springsecuritytms.controller.openapi;

import by.kolesnik.springsecuritytms.dto.ErrorResponse;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import by.kolesnik.springsecuritytms.dto.user.UserUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Работа с пользователями", description = "Данный контроллер позволяет выполнять CRUD-операции над пользователями")
public interface UserOpenApi {

    @Operation(
            method = "GET",
            summary = "Получить список всех пользователей",
            description = "Выводит список всех пользователей, содержащихся в базе данных",
            tags = "Работа с пользователями",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Список пользователей успешно получен",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserGetBasicDto.class)),
                            examples = @ExampleObject("""
                                    [
                                        {
                                            "id": 1,
                                            "name": "John"
                                            "role": "ROLE_ADMIN"
                                        }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Внутренняя ошибка сервера",
                    responseCode = "500",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Неизвестная ошибка сервера"
                                    }
                                    """)
                    )
            )
    })
    List<UserGetBasicDto> findAll();

    @Operation(
            method = "GET",
            summary = "Получить пользователя по id",
            description = "Выводит пользователя с указанным id из базы",
            tags = "Работа с пользователями",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Пользователь успешно получен",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserGetDto.class)),
                            examples = @ExampleObject("""
                                    [
                                        {
                                            "id": 1,
                                            "name": "John",
                                            "role": "ROLE_USER",
                                            groups: [
                                                {
                                                    "id": 3,
                                                    "name": "development"
                                                }
                                            ],
                                            tasks: []
                                        }
                                    ]
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Внутренняя ошибка сервера",
                    responseCode = "500",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Неизвестная ошибка сервера"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Страница не найдена",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "user with id=5 not found"
                                    }
                                    """)
                    )
            )
    })
    UserGetDto findById(@PathVariable Long id);

    @Operation(
            method = "PATCH",
            summary = "Изменить роль пользователя",
            description = "Устанавливает пользователю с указанным id указанную роль",
            tags = "Работа с пользователями",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Пользователь успешно обновлен",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserGetBasicDto.class)),
                            examples = @ExampleObject("""
                                    {
                                         "id": 2,
                                         "name": "user",
                                         "role": "ROLE_ADMIN"
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Внутренняя ошибка сервера",
                    responseCode = "500",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Неизвестная ошибка сервера"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Страница не найдена",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "user with id=7 not found"
                                    }
                                    """)
                    )
            )
    })
    UserGetBasicDto update(@PathVariable Long id, @RequestBody UserUpdateDto dto);

    @Operation(
            method = "DELETE",
            summary = "Удалить пользователя по id",
            description = "Удаляет пользователя с указанным id из базы",
            tags = "Работа с пользователями",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Пользователь успешно удален",
                    responseCode = "204"
            ),
            @ApiResponse(
                    description = "Внутренняя ошибка сервера",
                    responseCode = "500",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "Неизвестная ошибка сервера"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Страница не найдена",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "user with id=5 not found"
                                    }
                                    """)
                    )
            )
    })
    void delete(@PathVariable Long id);
}
