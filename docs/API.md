# API Documentation

## Overview

This document provides an overview of the API endpoints available in the application. Each endpoint is described with its purpose, request method, URL, parameters, and response format.

## Endpoints

### 1. User Registration

- **URL:** `/api/register`
- **Method:** `POST`
- **Description:** Registers a new user in the system.
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string",
    "email": "string"
  }
  ```
- **Response:**
  - **201 Created**: User successfully registered.
  - **400 Bad Request**: Invalid input data.

### 2. User Login

- **URL:** `/api/login`
- **Method:** `POST`
- **Description:** Authenticates a user and returns a JWT token.
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response:**
  - **200 OK**: Returns JWT token.
  - **401 Unauthorized**: Invalid credentials.

### 3. Get User Profile

- **URL:** `/api/user/profile`
- **Method:** `GET`
- **Description:** Retrieves the profile information of the authenticated user.
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
  - **200 OK**: Returns user profile data.
  - **401 Unauthorized**: Invalid or missing token.

### 4. Update User Profile

- **URL:** `/api/user/profile`
- **Method:** `PUT`
- **Description:** Updates the profile information of the authenticated user.
- **Headers:**
  - `Authorization: Bearer <token>`
- **Request Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response:**
  - **200 OK**: Profile successfully updated.
  - **400 Bad Request**: Invalid input data.
  - **401 Unauthorized**: Invalid or missing token.

### 5. Logout

- **URL:** `/api/logout`
- **Method:** `POST`
- **Description:** Logs out the authenticated user.
- **Headers:**
  - `Authorization: Bearer <token>`
- **Response:**
  - **200 OK**: Successfully logged out.
  - **401 Unauthorized**: Invalid or missing token.

## Error Handling

All API responses include a standard error format:

```json
{
  "error": {
    "code": "string",
    "message": "string"
  }
}
```

## Conclusion

This API documentation provides a comprehensive overview of the available endpoints and their usage. For further details, please refer to the source code or contact the development team.