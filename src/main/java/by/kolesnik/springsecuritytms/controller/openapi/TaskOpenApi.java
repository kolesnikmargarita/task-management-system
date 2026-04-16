package by.kolesnik.springsecuritytms.controller.openapi;

import by.kolesnik.springsecuritytms.dto.ErrorResponse;
import by.kolesnik.springsecuritytms.dto.task.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Работа с задачами", description = "Данный контроллер позволяет выполнять CRUD-операции над задачами")
public interface TaskOpenApi {

    @Operation(
            method = "GET",
            summary = "Получить список всех задач",
            description = "Выводит список всех задач, содержащихся в базе данных",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Список задач успешно получен",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskGetDto.class)),
                            examples = @ExampleObject("""
                                    [
                                          {
                                              "assignedUser": {
                                                  "id": 1,
                                                  "name": "string",
                                                  "role": "ROLE_ADMIN"
                                              },
                                              "createDateTime": "2026-04-15T04:38:31.046842",
                                              "creator": {
                                                  "id": 1,
                                                  "name": "string",
                                                  "role": "ROLE_ADMIN"
                                              },
                                              "deadlineDate": "2026-04-25T13:20:00",
                                              "description": "add new feature",
                                              "group": {
                                                  "id": 2,
                                                  "name": "QA"
                                              },
                                              "id": 1,
                                              "priority": "LOW",
                                              "status": "DONE"
                                          }
                                      ]
                                    """)
                    )
            )
    })
    List<TaskGetDto> findAll();

    @Operation(
            method = "GET",
            summary = "Получить список всех задач для текущего пользователя",
            description = "Выводит список всех задач, содержащихся в базе данных и назначенных текущему пользователю",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Список задач успешно получен",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskGetDto.class)),
                            examples = @ExampleObject("""
                                    [
                                          {
                                              "assignedUser": {
                                                  "id": 1,
                                                  "name": "string",
                                                  "role": "ROLE_ADMIN"
                                              },
                                              "createDateTime": "2026-04-15T04:38:31.046842",
                                              "creator": {
                                                  "id": 1,
                                                  "name": "string",
                                                  "role": "ROLE_ADMIN"
                                              },
                                              "deadlineDate": "2026-04-25T13:20:00",
                                              "description": "add new feature",
                                              "group": {
                                                  "id": 2,
                                                  "name": "QA"
                                              },
                                              "id": 1,
                                              "priority": "LOW",
                                              "status": "DONE"
                                          }
                                      ]
                                    """)
                    )
            )
    })
    List<TaskGetDto> findAllForCurrentUser();

    @Operation(
            method = "GET",
            summary = "Получить задачу по id",
            description = "Выводит задачи с указанным id",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Задача успешно получена",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskGetDto.class)),
                            examples = @ExampleObject("""
                                    {
                                          "assignedUser": {
                                              "id": 1,
                                              "name": "string",
                                              "role": "ROLE_ADMIN"
                                          },
                                          "createDateTime": "2026-04-15T04:38:31.046842",
                                          "creator": {
                                              "id": 1,
                                              "name": "string",
                                              "role": "ROLE_ADMIN"
                                          },
                                          "deadlineDate": "2026-04-25T13:20:00",
                                          "description": "add new feature",
                                          "group": {
                                              "id": 2,
                                              "name": "QA"
                                          },
                                          "id": 1,
                                          "priority": "LOW",
                                          "status": "DONE"
                                      }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Задача не найдена",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                        "message": "task with id=8 not found"
                                    }
                                    """)
                    )
            )
    })
    TaskGetDto findById(@PathVariable Long id);

    @Operation(
            method = "GET",
            summary = "Получить задачу текущего пользователя по id",
            description = "Выводит указанной задачи из базы данных из числа задач текущего пользователя",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Задача успешно получена",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskGetDto.class)),
                            examples = @ExampleObject("""
                                    {
                                        "assignedUser": {
                                            "id": 1,
                                            "name": "string",
                                            "role": "ROLE_ADMIN"
                                        },
                                        "createDateTime": "2026-04-15T04:38:31.046842",
                                        "creator": {
                                            "id": 1,
                                            "name": "string",
                                            "role": "ROLE_ADMIN"
                                        },
                                        "deadlineDate": "2026-04-25T13:20:00",
                                        "description": "add new feature",
                                        "group": {
                                            "id": 2,
                                            "name": "QA"
                                        },
                                        "id": 1,
                                        "priority": "LOW",
                                        "status": "DONE"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Указанная задача не найдена",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                          "message": "task with id=8 not found"
                                    }
                                    """)
                    )
            )
    })
    TaskGetDto findByIdForCurrentUser(@PathVariable Long id);

    @Operation(
            method = "POST",
            summary = "Добавить новую задачу",
            description = "Добавляет новую задачу в базу данных",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Задача успешно сохранена",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskGetBasicDto.class)),
                            examples = @ExampleObject("""
                                    {
                                         "createDateTime": "2026-04-15T04:38:31.0468423",
                                         "creator": {
                                             "id": 1,
                                             "name": "string",
                                             "role": "ROLE_ADMIN"
                                         },
                                         "deadlineDate": "2026-04-23T13:00:00",
                                         "description": "test new feature",
                                         "group": {
                                             "id": 2,
                                             "name": "QA"
                                         },
                                         "id": 1,
                                         "priority": "MEDIUM",
                                         "status": "CREATED"
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Указаннный пользователь не найден",
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
                    description = "Указанная группа не найдена",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                         "message": "group with id=5 not found"
                                     }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Дедлайне раньше даты создания",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                          "message": "deadline should be in future"
                                    }
                                    """)
                    )
            )
    })
    TaskGetBasicDto create(@RequestBody TaskCreateDto dto);

    @Operation(
            method = "PATCH",
            summary = "Изменить задачу",
            description = "Меняет поля значения полей задачи на указанные пользователем",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Задача успешно изменена",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskGetDto.class)),
                            examples = @ExampleObject("""
                                    {
                                          "assignedUser": {
                                              "id": 1,
                                              "name": "string",
                                              "role": "ROLE_ADMIN"
                                          },
                                          "createDateTime": "2026-04-15T04:38:31.046842",
                                          "creator": {
                                              "id": 1,
                                              "name": "string",
                                              "role": "ROLE_ADMIN"
                                          },
                                          "deadlineDate": "2026-04-25T13:20:00",
                                          "description": "add new feature",
                                          "group": {
                                              "id": 2,
                                              "name": "QA"
                                          },
                                          "id": 1,
                                          "priority": "LOW",
                                          "status": "DONE"
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
                                         "message": "user with id=7 not found"
                                    }
                                    """)
                    )
            )
    })
    TaskGetDto update(@PathVariable Long id, @RequestBody TaskUpdateDto dto);

    @Operation(
            method = "PATCH",
            summary = "Изменить статус задачи",
            description = "Меняет статус задачи с указанным id на указанный статус",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Статус задачи успешно изменен",
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = TaskGetDto.class)),
                            examples = @ExampleObject("""
                                    {
                                          "assignedUser": {
                                              "id": 1,
                                              "name": "string",
                                              "role": "ROLE_ADMIN"
                                          },
                                          "createDateTime": "2026-04-15T04:38:31.046842",
                                          "creator": {
                                              "id": 1,
                                              "name": "string",
                                              "role": "ROLE_ADMIN"
                                          },
                                          "deadlineDate": "2026-04-23T13:00:00",
                                          "description": "add new feature",
                                          "group": {
                                              "id": 2,
                                              "name": "QA"
                                          },
                                          "id": 1,
                                          "priority": "MEDIUM",
                                          "status": "DONE"
                                      }
                                    """)
                    )
            ),
            @ApiResponse(
                    description = "Указанный пользователь не найден в указанной группе",
                    responseCode = "404",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)),
                            examples = @ExampleObject("""
                                    {
                                         "message": "user with id=1 not found in this group"
                                     }
                                    """)
                    )
            )
    })
    TaskGetDto updateStatus(@PathVariable Long id, @RequestBody TaskUserUpdateDto dto);


    @Operation(
            method = "DELETE",
            summary = "Удалить задачу по id",
            description = "Удаляет задачу с указанным id из базы",
            tags = "Работа с задачами",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Задача успешно удалена",
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
