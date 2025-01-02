package com.example.springopenapiexample;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
// Default success response code for all endpoints
@ApiResponses(@ApiResponse(responseCode = "200", useReturnTypeSchema = true))
public class UserController {

    @PostMapping
    @SecurityRequirements
    public UserDto addUser(@RequestBody @Validated UserDto userDto) {
        return userDto;
    }

    @GetMapping("/{email}")
    // 404's response body will be added via custom open api configuration
    @ApiResponse(responseCode = "404", description = "User not found")
    public UserDto getUserByEmail(@Parameter(description = "user's email", example = "example@mail.com")
                                  @PathVariable @Email String email) {
        return new UserDto(1L, "retrieved user", email, null);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                           @RequestParam(required = false, defaultValue = "10") @Min(1) int size) {
        return List.of(new UserDto(1L, "retrieved user", "example@mail.com", null));
    }

    @GetMapping("/page")
    public Page<UserDto> getAllUsersAsPage(Pageable pageable) {
        return new PageImpl<>(
                List.of(new UserDto(1L, "retrieved user", "example@mail.com", null)),
                pageable, 1);
    }
}