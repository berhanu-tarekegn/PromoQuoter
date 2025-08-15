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

To run tests separately, use the following Gradle commands:

```bash
./gradlew test --tests "com.kifiya.PromoQuoter.cart.CartControllerUnitTest"
./gradlew test --tests "com.kifiya.PromoQuoter.cart.CartServiceUnitTest"
./gradlew test --tests "com.kifiya.PromoQuoter.promotion.PromotionServiceUnitTest"
```

## API Endpoints

### Cart Endpoints

#### Get Quote

- **URL**: `/cart/quote`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "items": [
      {
        "productId": "123e4567-e89b-12d3-a456-426614174000",
        "quantity": 2
      }
    ]
  }
  ```

#### Confirm Order

- **URL**: `/cart/confirm`
- **Method**: `POST`
- **Headers**: 
  - `Idempotency-Key`: `123e4567-e89b-12d3-a456-426614174000`
- **Request Body**:
  ```json
  {
    "items": [
      {
        "productId": "123e4567-e89b-12d3-a456-426614174000",
        "quantity": 2
      }
    ]
  }
  ```

### Promotion Endpoints

#### Get All Promotions

- **URL**: `/promotions`
- **Method**: `GET`

#### Save Promotion

- **URL**: `/promotions`
- **Method**: `POST`

##### Percent Off Category Promotion

- **Request Body**:
  ```json
  {
    "name": "Summer Sale",
    "discountPercentage": 15,
    "promotionType": "PERCENT_OFF_CATEGORY",
    "category": "Electronics"
  }
  ```

##### Buy X Get Y Promotion

- **Request Body**:
  ```json
  {
    "name": "Buy 2 Get 1 Free",
    "promotionType": "BUY_X_GET_Y",
    "productId": "123e4567-e89b-12d3-a456-426614174000",
    "buyQuantity": 2,
    "getQuantityFree": 1
  }
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

## Build and Run with Gradle

### Building the Project

To build the project, use the following command:

```bash
./gradlew build
```

### Running the Application

To run the application, use the following command:

```bash
./gradlew bootRun
```

### Running Tests

To run tests separately, use the following Gradle commands:

```bash
./gradlew test --tests "com.kifiya.PromoQuoter.cart.CartControllerUnitTest"
./gradlew test --tests "com.kifiya.PromoQuoter.cart.CartServiceUnitTest"
./gradlew test --tests "com.kifiya.PromoQuoter.promotion.PromotionServiceUnitTest"
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

## License

This project is licensed under the MIT License.
