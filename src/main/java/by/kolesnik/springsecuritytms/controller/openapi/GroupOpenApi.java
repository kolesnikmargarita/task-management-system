package by.kolesnik.springsecuritytms.controller.openapi;

import by.kolesnik.springsecuritytms.dto.ErrorResponse;
import by.kolesnik.springsecuritytms.dto.group.*;
import by.kolesnik.springsecuritytms.dto.user.UserGetBasicDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Работа с группами", description = "Данный контроллер позволяет выполнять CRUD-операции над группами")
public interface GroupOpenApi {

    @Operation(
            method = "GET",
            summary = "Получить список всех групп",
            description = "Выводит список всех групп, содержащихся в базе данных",
            tags = "Работа с группами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Список групп успешно получен",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserGetBasicDto.class)),
                            examples = @ExampleObject("""
                                    [
                                         {
                                             "id": 1,
                                             "name": "development"
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
    List<GroupGetBasicDto> findAll();

    @Operation(
            method = "GET",
            summary = "Получить группу по id",
            description = "Выводит группу с указанным id из базы данных",
            tags = "Работа с группами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Группа с указанным id успешно получена",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserGetBasicDto.class)),
                            examples = @ExampleObject("""
                                    {
                                        "id": 1,
                                        "name": "development",
                                        "tasks": [],
                                        "users": []
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
            )
    })
    GroupGetDto findById(@PathVariable Long id);

    @Operation(
            method = "POST",
            summary = "Добавление новой группы",
            description = "ВДобавление новой группы в базу данных",
            tags = "Работа с группами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Группа успешно добавлена",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GroupGetBasicDto.class)),
                            examples = @ExampleObject("""
                                    {
                                         "id": 1,
                                         "name": "development"
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
            )
    })
    GroupGetBasicDto create(@RequestBody GroupCreateDto dto);

    @Operation(
            method = "PATCH",
            summary = "Изменить группу",
            description = "Меняет название группы с указанным id на введеннное пользователем",
            tags = "Работа с группами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Наименование группы успешно измененно",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GroupGetDto.class)),
                            examples = @ExampleObject("""
                                    {
                                         "id": 1,
                                         "name": "QA"
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
            )
    })
    GroupGetBasicDto update(@PathVariable Long id, @RequestBody GroupUpdateDto dto);

    @Operation(
            method = "PATCH",
            summary = "Добавить студента в группу",
            description = "Добавляет пользователя в группу",
            tags = "Работа с группами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "пользователь успешнро добавлен в группу",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GroupGetDto.class)),
                            examples = @ExampleObject("""
                                    {
                                          "id": 1,
                                          "name": "QA",
                                          "tasks": [],
                                          "users": [
                                              {
                                                  "id": 2,
                                                  "name": "user",
                                                  "role": "ROLE_USER"
                                              }
                                          ]
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
                    description = "Пользователь не найден",
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
            ),
            @ApiResponse(
                    description = "Группа не найдена",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                          "message": "group with id=7 not found"
                                    }
                                    """)
                    )
            )
    })
    GroupGetDto addUser(@PathVariable Long id, @RequestBody GroupUserAddDto userAddDto);

    @Operation(
            method = "DELETE",
            summary = "Удалить группу по id",
            description = "Удаляет группу с указанным id из базы",
            tags = "Работа с группами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Группа успешно удалена",
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
