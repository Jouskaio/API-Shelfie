# API-Shelfie

App that lets you scan the barcodes of your physical books and build your own digital library. Easily manage, organize, and explore your collection online.

## Clean Architecture Implementation

This project implements a **Clean Architecture** for a Java REST API using Spring Boot, following Domain-Driven Design principles.

### Architecture Layers

#### 1. Domain Layer (`com.shelfie.domain`)
The core business logic layer, containing:
- **Entities**: `Book`, `User` with rich business behavior
- **Value Objects**: `BookId`, `UserId` for type safety
- **Enums**: `BookStatus` for book states
- **Domain Services**: `BookDomainService` for cross-entity business logic
- **Repository Interfaces**: Contracts for data persistence

#### 2. Application Layer (`com.shelfie.application`)
Orchestrates business workflows:
- **Use Cases**: `BookManagementUseCase` for application operations
- **DTOs**: Request/Response data transfer objects
- **Mappers**: Convert between domain entities and DTOs

#### 3. Infrastructure Layer (`com.shelfie.infrastructure`)
Technical implementation details:
- **Persistence**: JPA entities and repository implementations
- **Configuration**: Spring configuration classes
- **External Services**: Ready for barcode scanning API integration

#### 4. Presentation Layer (`com.shelfie.presentation`)
REST API interface:
- **Controllers**: REST endpoints with OpenAPI documentation
- **Exception Handlers**: Global error handling and validation

### API Endpoints

#### Books Management
- `POST /api/v1/books` - Add a new book
- `GET /api/v1/books/{id}` - Get book by ID
- `GET /api/v1/books/barcode/{barcode}` - Get book by barcode
- `GET /api/v1/books` - Get all books (with search/filter options)
- `PUT /api/v1/books/{id}` - Update book details
- `PATCH /api/v1/books/{id}/status` - Update book status
- `DELETE /api/v1/books/{id}` - Delete a book
- `GET /api/v1/books/stats` - Get library statistics

### Quick Start

1. **Prerequisites**: Java 17+, Maven 3.6+

2. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the API**:
   - Application: http://localhost:8081
   - API Documentation: http://localhost:8081/swagger-ui.html
   - OpenAPI Spec: http://localhost:8081/v3/api-docs

4. **Test the API**:
   ```bash
   # Add a book
   curl -X POST http://localhost:8081/api/v1/books \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Clean Architecture",
       "author": "Robert C. Martin",
       "isbn": "9780134494166",
       "barcode": "123456789",
       "description": "A guide to software architecture",
       "genre": "Technology"
     }'
   
   # Get all books
   curl http://localhost:8081/api/v1/books
   ```

### Development

- **Build**: `mvn clean compile`
- **Test**: `mvn test`
- **Package**: `mvn clean package`

### Database

- PostgreSQL (configuration ready)

### Key Features

✅ **Clean Architecture** with proper dependency inversion  
✅ **Domain-Driven Design** with rich domain entities  
✅ **RESTful API** with comprehensive CRUD operations  
✅ **Data Validation** with Bean Validation  
✅ **Error Handling** with global exception handlers  
✅ **API Documentation** with OpenAPI/Swagger  
✅ **Database Integration** with Spring Data JPA  
✅ **Testing** with JUnit 5 and Spring Boot Test  
✅ **Security** basic configuration (ready for enhancement)

### Next Steps

- [ ] Add User authentication and authorization
- [ ] Implement barcode scanning service integration
- [ ] Add book recommendation engine
- [ ] Implement library sharing features
- [ ] Add book cover image support
- [ ] Deploy with Docker containerization
- [ ] Set up CI/CD pipeline
