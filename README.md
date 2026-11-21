# Gadget Store API

A REST API for a gadget store (categories, brands, products) built to demonstrate **API versioning**. Two versions of the Product API (`/api/v1` and `/api/v2`) run side by side on top of one shared service layer, and v1 is marked as deprecated using standard HTTP headers.

## Why this project exists

Clients such as old mobile apps cannot always be force-updated. When a response shape has to change in a breaking way, the old shape must keep working while the new one is introduced. This project shows how to do that without duplicating business logic.

## Tech stack

- Java 21
- Spring Boot 3.5.x (Web, Data JPA, Validation)
- PostgreSQL
- Lombok
- springdoc-openapi (Swagger UI)
- Maven

## Versioning approach

- **Strategy:** URI versioning (`/api/v1/...`, `/api/v2/...`)
- **Shared service layer:** `ProductService` knows nothing about versions. It returns a neutral `ProductResult` record.
- **Per-version edge:** each version has its own response DTO, mapper and controller.
- **Deprecation:** all `/api/v1/products/**` responses carry `Deprecation`, `Sunset` and `Link` headers, added by a `HandlerInterceptor`. Dates come from `application.properties`.

```
Controller v1 -> ProductV1Mapper -> ProductResponseV1  \
                                                         -> ProductService -> ProductResult (neutral)
Controller v2 -> ProductV2Mapper -> ProductResponseV2  /
```

## The breaking change (Product response)

| Field | v1 | v2 |
|---|---|---|
| price | `49999.00` | `{ "amount": 49999.00, "currency": "INR" }` |
| brand | `"brandName": "Samsung"` | `"brand": { "id": 1, "name": "Samsung" }` |
| category | `"categoryName": "Phones"` | `"category": { "id": 1, "name": "Phones" }` |
| createdAt | not present | `"createdAt": "2026-11-21T11:30:00"` |

Request bodies are identical in both versions.

## Endpoints

| Resource | Method and path | Notes |
|---|---|---|
| Category | `POST/GET /api/v1/categories`, `GET/PUT/DELETE /api/v1/categories/{id}` | v1 only |
| Brand | `POST/GET /api/v1/brands`, `GET/PUT/DELETE /api/v1/brands/{id}` | v1 only |
| Product | `POST/GET /api/v1/products`, `GET/PUT/DELETE /api/v1/products/{productId}` | **Deprecated**, v1 response shape |
| Product | `POST/GET /api/v2/products`, `GET/PUT/DELETE /api/v2/products/{productId}` | v2 response shape |

Deleting a category or brand that still has products returns **409 Conflict**.

## Deprecation headers (v1 products)

```
Deprecation: @1795219200
Sunset: Wed, 30 Jun 2027 23:59:59 GMT
Link: </api/v2/products/1>; rel="successor-version"
```

## Other things covered

- Entity / DTO / Mapper / Repository / Service / Controller layering
- Bean Validation with a global exception handler (400, 404, 409)
- `@ManyToOne` (LAZY) relationships, `BigDecimal` for money
- N+1 query problem found from the SQL log and fixed with `JOIN FETCH` JPQL queries
- Lombok on entities and DTOs, Java records for v2 responses and the neutral result model

## Running locally

1. Create the database:
   ```sql
   CREATE DATABASE gadgetstore_db;
   ```
2. Set your credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/gadgetstore_db
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_PASSWORD
   app.api.v1.deprecated-on=2026-11-21
   app.api.v1.sunset-date=2027-06-30
   ```
3. Start the app:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Open Swagger UI at `http://localhost:8080/swagger-ui.html`.

## Project structure

```
com.backend.gadgetstore
├── config        (DeprecationInterceptor, WebConfig)
├── controller
│   ├── v1
│   └── v2
├── dto
│   ├── request
│   ├── response (v1, v2)
│   └── result   (neutral ProductResult)
├── entity
├── exception
├── mapper
├── repository
└── service
```