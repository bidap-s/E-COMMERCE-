# E-Commerce API

A backend REST API for an e-commerce store, built with Spring Boot. It supports browsing products, anonymous cart building, user registration and JWT-based authentication, role-based access for admins, and order checkout.

There is no frontend — every endpoint is designed to be consumed by a client (web, mobile, or tested directly via Postman/Swagger).

## Features

- Product and category browsing, with filtering by category
- Anonymous shopping carts (no login required to browse or add items)
- User registration and login with JWT authentication
- Role-based authorization (`USER` / `ADMIN`)
- Checkout flow that converts a cart into an order
- Order history for logged-in users
- Centralized error handling with consistent JSON error responses
- Auto-generated API documentation via Swagger UI

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4.1 |
| Data access | Spring Data JPA / Hibernate |
| Database | MySQL |
| Migrations | Flyway |
| Auth | Spring Security + JWT (jjwt) |
| Object mapping | MapStruct |
| Validation | Jakarta Bean Validation |
| Docs | springdoc-openapi (Swagger UI) |
| Build tool | Maven |
| Boilerplate reduction | Lombok |

## Project Structure

This project follows a **package-by-feature** structure — each business feature has its own package containing its entities, repository, controller, DTOs, and mapper, rather than grouping by technical layer.

```
com.example.ecommerce
├── products/    Product and Category management
├── users/       User accounts, addresses, profiles
├── carts/       Shopping cart and cart items
├── orders/      Orders, order items, checkout
├── auth/        Login, JWT generation and validation
├── admin/       Admin-only endpoints
└── common/      Shared exception handling, security rules interface
```

## Getting Started

### Prerequisites
- Java 17
- Maven (or use the included `mvnw` wrapper)
- MySQL running locally

### 1. Clone the repository
```
git clone <your-repo-url>
cd ecommerce
```

### 2. Configure the database
Open `src/main/resources/application-dev.yaml` and set your local MySQL credentials.

### 3. Configure environment variables
Copy `.env.example` to `.env`:
```
cp .env.example .env
```
Set `JWT_SECRET` to a securely generated random value:
```
openssl rand -base64 32
```

### 4. Run the application
```
./mvnw spring-boot:run
```
Windows:
```
mvnw.cmd spring-boot:run
```

The app runs on `http://localhost:8080`. Flyway automatically creates the schema and seeds sample product data on first run.

### 5. Explore the API
Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Example API Flow

1. **Browse products** — `GET /products`
2. **Create a cart** (no login needed) — `POST /carts`
3. **Add an item to the cart** — `POST /carts/{cartId}/items`
   ```json
   { "productId": 1 }
   ```
4. **Register a user** — `POST /users`
   ```json
   { "name": "Jane Doe", "email": "jane@example.com", "password": "password123" }
   ```
5. **Log in** — `POST /auth/login`
   ```json
   { "email": "jane@example.com", "password": "password123" }
   ```
   Returns a JWT access token.
6. **Checkout** — `POST /checkout` (requires `Authorization: Bearer <token>`)
   ```json
   { "cartId": "<cart-id-from-step-2>" }
   ```
   Creates an order from the cart and marks it as paid.
7. **View order history** — `GET /orders` (requires the same JWT)

## Notes on Design Decisions

- **No real payment gateway is integrated.** Checkout creates the order and marks it paid directly. This was a deliberate scope decision to keep the project focused on core REST API, JPA, and security fundamentals rather than third-party payment integration.
- **DTOs are used throughout** rather than exposing JPA entities directly in API responses, to avoid leaking internal fields (like password hashes) and to decouple the API contract from the database schema.
- **Secrets are never hardcoded.** `JWT_SECRET` is loaded from a `.env` file (via `spring-dotenv`), which is excluded from version control.

## License

This project was built for learning and portfolio purposes.
