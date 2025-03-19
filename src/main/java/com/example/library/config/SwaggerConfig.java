package com.example.library.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig  {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management API")
                        .version("v3")
                        .description("API for managing books and users in the library system"))
                        .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecuritySchema"))
                        .components(new Components().addSecuritySchemes("JavaInUseSecuritySchema",new SecurityScheme()
                                .name("JavaInUseSecuritySchema").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
