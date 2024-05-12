package com.example.springopenapiexample;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
//дефолтный ответ для всех запросов
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class UserController {

    @PostMapping
    @SecurityRequirements
    public UserDto addUser(@RequestBody @Validated UserDto userDto) {
        return userDto;
    }

    @GetMapping("/{email}")
    // тело ответа добавится в конфигурации
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public UserDto getUserByEmail(@PathVariable @Validated @Email
                                  @Parameter(description = "Электронная почта", example = "junior@example.com")
                                  String email) {
        return new UserDto("retrieved user", email, null);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "0")
                                           @Parameter(description = "min: 0") //раз
                                           @Validated @Min(0) int page,
                                           @RequestParam(required = false, defaultValue = "10")
                                           @Parameter(description = "min: 1") //двас
                                           @Validated @Min(1) int size) {
        return List.of(new UserDto("retrieved user", "junior@example.com", null));
    }

    @GetMapping("/page")
    public Page<UserDto> getAllUsersAsPage(Pageable pageable) {
        return Page.empty(pageable);
    }
}
