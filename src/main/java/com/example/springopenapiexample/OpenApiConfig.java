package com.example.springopenapiexample;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Пример генерации OpenAPI из Spring MVC", version = "1.0.0"),
        security = @SecurityRequirement(name = "BearerAuth"))
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        name = "BearerAuth")
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            //Создаем ссылку на схему ошибка
            var sharedErrorSchema = new Schema<>().$ref("#/components/schemas/" + ErrorResponseDto.class.getSimpleName());

            //Добавляем тело ответа ко всем ошибкам
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation ->
                    operation.getResponses().forEach((status, response) -> {
                        if (status.startsWith("4") || status.startsWith("5")) {
                            response.getContent().forEach((code, mediaType) -> mediaType.setSchema(sharedErrorSchema));
                        }
                    })));
        };
    }

    /**
     * Костыль, чтобы добавить схему ошибки в open api
     */
    @RestController
    @RequestMapping("/donotuse")
    static public class DoNotUse {

        @Operation(description = "Не использовать. Костыль, необходимый для конфигурации open api",
                deprecated = true,
                responses = {
                        @ApiResponse(content = @Content(schema =
                        @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDto.class)))
                })
        @DeleteMapping
        public void registerOpenApiComponent() {
        }
    }
}
