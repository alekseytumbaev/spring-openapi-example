package com.example.springopenapiexample;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@AllArgsConstructor
@Schema(description = "User")
public class UserDto {

    @JsonProperty(access = READ_ONLY)
    private Long id;

    private String name;

    @Schema(description = "user's email", example = "example@mail.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Password must contain at least one letter, one number and one special character")
    @Size(min = 8, max = 32)
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?^&])[A-Za-z\\d@$!%*#?^&]{3,}$")
    @JsonProperty(access = WRITE_ONLY)
    private String password;
}
