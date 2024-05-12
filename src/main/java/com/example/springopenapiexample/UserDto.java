package com.example.springopenapiexample;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Пользователь")
public class UserDto {

    private String name;

    @Schema(description = "Электронная почта", example = "junior@example.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Пароль должен содержать от 8 до 32 символов," +
            "как минимум одну букву, одну цифру и один специальный символ")
    @Size(min = 8, max = 32)
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?^&])[A-Za-z\\d@$!%*#?^&]{3,}$")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
