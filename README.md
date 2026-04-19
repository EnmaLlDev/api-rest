# api-rest

Proyecto Udam2 - en desarrollo

API REST construida con Spring Boot para gestionar los servicios del proyecto Udam2.

## Resumen

Microservicio / API monolítica ligera basada en Spring Boot que expone endpoints REST para la aplicación. Diseñado con una arquitectura en capas (Controller → Service → Repository) y con seguridad por JWT.

## Stack tecnológico

- Lenguaje: Java 21
- Build: Gradle (Kotlin DSL)
- Framework principal: Spring Boot 3.5.6
- Persistencia: Spring Data JPA (Hibernate)
- Base de datos: MySQL
- Seguridad: Spring Security + JWT (jjwt)
- Utilidades: Lombok, Apache Commons CSV
- Tests: Spring Boot Test, JUnit Platform

(Ver dependencias en `build.gradle.kts`)

## Arquitectura

La aplicación sigue una arquitectura en capas típica:

- Controllers: Exponen la API REST y validan peticiones.
- Services: Lógica de negocio y orquestación.
- Repositories: Interfaces JPA para acceso a datos.
- Models / Entities: Clases JPA que representan tablas.
- DTOs: Objetos de transferencia para requests/responses.
- Security: JWT filters, UserDetailsService, configuración de Spring Security.
- Config: Datasource, CORS, beans y otras configuraciones.
- Exception handling: Manejo centralizado con `@ControllerAdvice`.

Paquete base sugerido: `com.fp`

## Estructura de directorios propuesta

```
src/
├─ main/
│  ├─ java/
│  │  └─ com/fp/
│  │     ├─ ApiRestApplication.java
│  │     ├─ config/
│  │     ├─ controller/
│  │     ├─ dto/
│  │     ├─ exception/
│  │     ├─ model/
│  │     ├─ repository/
│  │     ├─ service/
│  │     └─ security/
│  └─ resources/
│     ├─ application.yml
│     ├─ data/
│     └─ static/
└─ test/
```

## Configuración y variables importantes

No guardar secretos en el repo. Variables mínimas:

- spring.datasource.url
- spring.datasource.username
- spring.datasource.password
- spring.jpa.hibernate.ddl-auto
- jwt.secret
- jwt.expirationMs
- spring.profiles.active

Ejemplo (application.properties):
```
spring.datasource.url=jdbc:mysql://localhost:3306/udam2_db
spring.datasource.username=root
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
jwt.secret=changeit
jwt.expirationMs=3600000
```

## Ejecutar localmente

Requisitos: JDK 21, Gradle wrapper, MySQL (o Docker).

1. Docker MySQL (opcional):
   docker run --name udam2-mysql -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=udam2_db -p 3306:3306 -d mysql:8
2. Ejecutar:
   ./gradlew bootRun
   o
   ./gradlew build
   java -jar build/libs/*.jar
3. Tests:
   ./gradlew test

## Endpoints (ejemplos)

- POST /api/auth/login — autenticar, devuelve JWT
- POST /api/auth/register — registrar usuario
- GET /api/items — listar recursos (autenticado)
- POST /api/items — crear recurso (roles)

Recomiendo documentar con OpenAPI (springdoc-openapi).

## Seguridad

- JWT (Bearer tokens) para autenticación.
- Roles/Authorities para autorización (@PreAuthorize / HttpSecurity).
- Considerar revocación/refresh tokens si aplica.

## Recomendaciones

- Migraciones con Flyway/Liquibase.
- CI (GitHub Actions): build, test, análisis estático.
- Docker Compose para desarrollo (DB + app).
- Herramientas de calidad: SonarCloud / SpotBugs.

## Contribuir

1. Fork
2. Crear rama `feature/<nombre>`
3. Abrir PR con descripción y tests si aplica

## Licencia

Añadir LICENSE (por ejemplo MIT) si corresponde.