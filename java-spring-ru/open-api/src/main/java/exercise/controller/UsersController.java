package exercise.controller;

import exercise.UserNotFoundException;
import exercise.model.User;
import exercise.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // Аннотация отмечает метод контроллера как операцию
    // И определяет краткую информацию об этой рперации
    @Operation(summary = "Get list of all users")
    // Аннотация определяет ответ, который может быть получен (код и описание)
    @ApiResponse(responseCode = "200", description = "List of all users")
    @GetMapping(path = "")
    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Operation(summary = "Get specific user by his id")
    // Контейнер для аннотаций @ApiResponse
    // Используется в случае, если нужно указать более одного ответа
    @ApiResponses(value = {
            // Указываем, что операция вернет ответ с кодом 200 в случае успешного выполнения
            @ApiResponse(responseCode = "200", description = "User found"),
            // И ответ с кодом 404 в случе, если пользователь не найден
            @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @GetMapping(path = "/{id}")
    public User getUser(
            // Аннотация отмечает параметр метода, как параметр для операции
            // И определяет его описание
            @Parameter(description = "Id of user to be found")
            @PathVariable long id) {

        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(
            @Parameter(description = "User data to save")
            @RequestBody User user) {
        return userRepository.save(user);
    }

    @Operation(summary = "Delete user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @DeleteMapping(path = "/{id}")
    public void deleteUser(
            @Parameter(description = "Id of user to be deleted")
            @PathVariable long id) {
        // Проверяем, существует ли пользователь с таким id
        if (!userRepository.existsById(id)) {
            // Если не существует, возвращаем код ответа 404
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Operation(summary = "Delete user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User with that id not found")
    })
    @PatchMapping(path = "/{id}")
    public void patchUser(@Parameter(description = "Id of user to be deleted") @PathVariable long id, @RequestBody User user) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        user.setId(id);
        userRepository.save(user);
    }
    // BEGIN

//    Выполните команду make api-doc, которая запустит gradle таску generateOpenApiDocs и запишет документацию в спецификации OpenAPI 3 в файл openapi.json в директорию build
//    build/openapi.json
//            Задачи
//    Изучите получившийся файл. Он представляет из себя документацию в спецификации OpenAPI 3 к нашему API
//            Подсказки
//    Изучите файлы с примерами в директории Examples
//
//    Посмотреть, какие записи будут добавлены в базу при старте приложения, можно в файле src/main/resources/import.sql

    // END
}
