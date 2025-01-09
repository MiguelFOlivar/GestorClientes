package org.mfigueroa.App.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;

@OpenAPIDefinition(
        info = @Info(
                title = "APP-productos-Swagger",
                description = "API REST para la gestión y control de productos",
                termsOfService = "www.terms-info.com",
                version = "1.0.0",
                contact = @Contact(
                        name = "Miguel Figueroa",
                        email = "mklawliet@gmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                )
        }
)
public class SwaggerConfig {

}
