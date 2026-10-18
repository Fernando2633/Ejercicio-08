# API de Pedidos – Ejercicio 08

API REST de **pedidos** (tabla 11) hecha con **Spring Boot**, **arquitectura por capas** y documentada con **OpenAPI 3 / Swagger UI** (springdoc-openapi).

Fernando Antonio García Ruiz – 7CM1 – UPIIZ IPN

## Capas

```
src/main/java/mx/ipn/upiiz/pedidos
├── configuration   SwaggerConfig (@OpenAPIDefinition, @Info, @Contact, @License, @Server)
├── controller      PedidoController (@Tag, @Operation, @ApiResponse)
├── service         PedidoService + impl/PedidoServiceImpl (reglas de negocio)
├── repository      PedidoRepository (Spring Data JPA)
├── model           Pedido (entidad)
├── dto             PedidoDTO (id READ_ONLY), ApiRespuesta, RespuestasSwagger
├── mapper          PedidoMapper
└── exception       GlobalExceptionHandler y excepciones propias
```

## Endpoints (versión 1)

| Método | Ruta | Códigos |
|---|---|---|
| POST | `/api/v1/pedidos` | 201, 400, 409, 500 |
| GET | `/api/v1/pedidos` | 200, 500 |
| GET | `/api/v1/pedidos/{id}` | 200, 400, 404, 500 |
| GET | `/api/v1/pedidos/buscar?estado=Pendiente` | 200, 400, 500 |
| PUT | `/api/v1/pedidos/{id}` | 200, 400, 404, 409, 500 |
| DELETE | `/api/v1/pedidos/{id}` | 200, 400, 404, 500 |

Todas las respuestas regresan `estado`, `mensaje` y, si aplica, `datos` o `errores`.

## Documentación

- Swagger UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`

## Correr en local

Darle Run a `ApiPedidosApplication` (usa H2 en memoria) y abrir http://localhost:8080/swagger-ui/index.html

## Render

Web Service con Docker y variables `DB_URL`, `DB_USER`, `DB_PASSWORD` (PostgreSQL).
