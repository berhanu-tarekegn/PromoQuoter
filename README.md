# PromoQuoter Application

## Overview

PromoQuoter is a Spring Boot application designed to manage promotions, carts, and orders. It provides RESTful APIs for handling various operations related to promotions and cart management.

## Features

### Main Features

- **Cart Management**: 
  - Add items to the cart.
  - Apply promotions to the cart.
  - Confirm orders.

- **Promotion Management**:
  - Create and manage different types of promotions.
  - Apply promotions to cart items.

- **Order Management**:
  - Create and manage orders with idempotency support.

### Key Components

- **CartController**: Handles HTTP requests related to cart operations.
- **PromotionController**: Manages promotion-related HTTP requests.
- **OrderService**: Provides services for order creation and management.

## Project Structure

### Main Source Code (`src/main/java/com/kifiya/PromoQuoter`)

- **cart**: Contains classes related to cart operations, including `CartController`, `CartService`, and `CartServiceImpl`.
- **promotion**: Manages promotion logic with classes like `PromotionService`, `PromotionServiceImpl`, and various promotion types.
- **order**: Handles order-related operations with `OrderService` and `OrderServiceImpl`.
- **common**: Includes shared utilities and configurations.

### Test Code (`src/test/java/com/kifiya/PromoQuoter`)

- **cart**: Contains unit tests for cart-related classes, such as `CartControllerUnitTest` and `CartServiceUnitTest`.
- **promotion**: Includes tests for promotion logic, like `PromotionServiceUnitTest`.
- **order**: Tests for order management.

## Build Configuration

### `build.gradle`

- **Plugins**: 
  - Java
  - Spring Boot
  - Dependency Management
  - JaCoCo for test coverage

- **Dependencies**:
  - Spring Boot Starters for Web, Data JPA, and Validation.
  - H2 Database for runtime.
  - Lombok for reducing boilerplate code.
  - JUnit and Mockito for testing.

### Lombok Configuration

- **Lombok** is used to reduce boilerplate code with annotations like `@Data`, `@Builder`, `@NoArgsConstructor`, etc.
- Ensure Lombok is correctly set up in your IDE for optimal development experience.

## Running Tests

To run tests separately, use the following Maven commands:

```bash
mvn -Dtest=CartControllerUnitTest test
mvn -Dtest=CartServiceUnitTest test
mvn -Dtest=PromotionServiceUnitTest test
```

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   ```

2. **Build the project**:
   ```bash
   ./gradlew build
   ```

3. **Run the application**:
   ```bash
   ./gradlew bootRun
   ```

4. **Access the application**:
   - The application runs on `http://localhost:8080`.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

## License

This project is licensed under the MIT License.
