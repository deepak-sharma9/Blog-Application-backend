package org.blogapplication.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define the security scheme name. You can call it "bearerAuth" or any other name.
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                // Apply the security requirement globally
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .info(new Info()
                        .title("Blog Application API")
                        .version("v1.0")
                        .description("API documentation for the Blog Application")
                        .contact(new Contact()
                                .name("Deepak Sharma")
                                .email("ideepaksharma5735@gmail.com")));
    }
}
