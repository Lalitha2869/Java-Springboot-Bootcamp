package com.example.book_api_day8.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book API")
                        .description("Spring Boot Book Management API")
                        .version("1.0"));
    }
}