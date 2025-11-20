package com.example.notesbackend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the Notes API.
 */
@Configuration
public class OpenApiConfig {

    // PUBLIC_INTERFACE
    /**
     * Configures OpenAPI metadata for the application.
     * @return OpenAPI instance with metadata.
     */
    @Bean
    public OpenAPI notesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notes API")
                        .version("1.0.0")
                        .description("REST API for creating, reading, updating, and deleting notes.")
                        .contact(new Contact().name("Notes Backend").email("no-reply@example.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Swagger UI")
                        .url("/swagger-ui.html"));
    }
}
