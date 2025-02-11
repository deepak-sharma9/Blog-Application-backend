# Blog-Application-backend
# Overview
* This project is a scalable and secure backend for a blog platform built using Spring Boot. It exposes RESTful APIs for managing blog posts, categories, comments, and user authentication. Designed with a modular structure and cloud deployment in mind, this application emphasizes best practices such as secure JWT flows, DTO validation, and centralized exception handling.

# Architecture Overview
* Spring Boot Backend: A robust and scalable backend framework providing RESTful APIs.
* Security Layer: JWT Authentication: Components such as JwtService, JwtAuthenticationFilter, and SecurityConfig handle token generation, validation, and role-based access control.
* Spring Security: Customized via CustomUserDetailService to implement user-specific security logic.
* Domain Entities: Core entities including Post, Category, Comment, User, and Roles define the data model and are mapped to a relational database like MySQL.
* Service Layer:Business logic is implemented in services like PostServiceImpl, UserServiceImpl, and CategoryServiceImpl, ensuring smooth CRUD operations.
                Data transfer between layers is streamlined using DTOs (e.g., PostDto, UserDto).
* API Controllers: REST endpoints are exposed through controllers such as PostController, UserController, and AuthController for frontend interaction.
* Infrastructure: Repositories: Spring Data JPA repositories (e.g., PostRepository, UserRepository) manage database operations.
* Exception Handling: A global exception handler (GlobalExceptionHandler) along with custom exceptions like ResourceNotFoundException provides consistent error management.

# Key Features
* User Management: Enables registration, login, and role-based permissions (admin vs. regular user).
* Post & Comment System: Supports full CRUD operations for blog posts and nested comments.
* Swagger Integration: API documentation and testing are facilitated through Swagger (configured with SwaggerConfig).
* Environment-Specific Configurations: Uses separate configuration files (application-dev.properties and application-prod.properties) tailored for development and AWS production environments.

# Technologies Used
* Backend Framework: Spring Boot, Spring MVC
* Security: Spring Security, JWT
* Persistence: Spring Data JPA, Hibernate, MySQL
* Documentation: Swagger
* Build Tool: Maven
* Deployment: AWS Elastic Beanstalk (for production with auto-scaling and load balancing)

 # Contact: ideepaksharma5735@gmail.com
