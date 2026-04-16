package by.kolesnik.springsecuritytms.controller.openapi;

import by.kolesnik.springsecuritytms.dto.ErrorResponse;
import by.kolesnik.springsecuritytms.dto.security.LoginRequestDto;
import by.kolesnik.springsecuritytms.dto.security.RegisterRequestDto;
import by.kolesnik.springsecuritytms.dto.security.TokenResponseDto;
import by.kolesnik.springsecuritytms.dto.user.UserGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Авторизация пользователей", description = "Данный контроллер позволяет зарегистрироваться новым пользователям и авторизоваться зарегистрированным пользователям")
public interface AuthOpenApi {

    @Operation(
            method = "POST",
            summary = "Зарегистрировать нового пользователя",
            description = "Добавляет пользователя в базу данных",
            tags = "Работа с авторизацией пользователей",
            security = @SecurityRequirement(name = "не требуется")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Пользователь успешно зарегистрирован",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TokenResponseDto.class)),
                            examples = @ExampleObject("""
                                    {
                                      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmc3IiwiaWF0IjoxNzc2MjExOTkyLCJleHAiOjE3NzY4MTY3OTJ9.VhwTo646QzEY1SUrpLu1ngxWHYp6NDs9-G12KKr5RD4"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Внутренняя ошибка сервера",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "повторяющееся значение ключа нарушает ограничение уникальности"
                                    }
                                    """)
                    )
            )
    })
    TokenResponseDto register(@RequestBody RegisterRequestDto dto);

    @Operation(
            method = "POST",
            summary = "Автооризация пользователя",
            description = "Авторизует пользователя и генерирует токен",
            tags = "Работа с авторизацией пользователей",
            security = @SecurityRequirement(name = "не требуется")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Пользователь успешно авторизован",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TokenResponseDto.class)),
                            examples = @ExampleObject("""
                                    {
                                      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmc3IiwiaWF0IjoxNzc2MjExOTkyLCJleHAiOjE3NzY4MTY3OTJ9.VhwTo646QzEY1SUrpLu1ngxWHYp6NDs9-G12KKr5RD4"
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
                    description = "Неверный логин или пароль",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                       "message": "Bad credentials"
                                     }
                                    """)
                    )
            )
    })
    TokenResponseDto login(@RequestBody LoginRequestDto dto);
}
