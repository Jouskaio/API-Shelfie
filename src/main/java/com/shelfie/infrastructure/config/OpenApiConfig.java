package com.shelfie.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI documentation
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI apiDocumentation() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shelfie")
                        .description("Digital library management API for scanned books")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Shelfie")
                                .email("contact@jouskaio.me"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://github.com/Jouskaio/API-Shelfie/blob/main/LICENSE")));
    }
}