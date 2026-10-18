package mx.ipn.upiiz.pedidos.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/** Configuracion de la documentacion OpenAPI 3 (Swagger UI). */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Pedidos",
                description = "API REST para administrar pedidos (tabla 11 – Pedido). "
                        + "Ejercicio 08 – Documentación y endpoints con Spring Boot, arquitectura por capas y OpenAPI 3.",
                termsOfService = "https://github.com/Fernando2633/Ejercicio-08",
                contact = @Contact(
                        name = "Fernando Antonio García Ruiz",
                        email = "Fernando2633@users.noreply.github.com",
                        url = "https://github.com/Fernando2633",
                        extensions = @Extension(name = "x-alumno", properties = {
                                @ExtensionProperty(name = "escuela", value = "UPIIZ-IPN"),
                                @ExtensionProperty(name = "grupo", value = "7CM1")
                        })),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
                version = "1.0.0"),
        servers = {
                @Server(url = "/", description = "Servidor actual"),
                @Server(url = "https://api-pedidos-fagr.onrender.com", description = "Render (producción)"),
                @Server(url = "http://localhost:8080", description = "Local")
        })
public class SwaggerConfig {
}
