# Nexus Project Backend Documentation

## Project Overview
This is a Spring Boot backend application for the Nexus project, designed to handle exam resources and user management.

## Project Structure
```
Backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── nexus/
│   │   │           └── nexusproject/
│   │   │               ├── Config/
│   │   │               ├── Controller/
│   │   │               ├── DTO/
│   │   │               ├── model/
│   │   │               ├── Repository/
│   │   │               ├── Service/
│   │   │               └── NexusprojectApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## Core Components

### 1. Models

#### User Model (`model/User.java`)
```java
package com.nexus.nexusproject.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
```

#### ExamResource Model (`model/ExamResource.java`)
```java
package com.nexus.nexusproject.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_resources")
public class ExamResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectCode;
    private String subjectName;
    private String professorName;

    @Column(name = "exam_type")
    private String type;          // "Mid Sem" or "End Sem"

    private Integer semester;     // 1-8
    private Integer year;         // e.g., 2023
    private String branch;        // CSE, ECE, etc.

    private String fileUrl;       // Cloudinary secure URL

    private LocalDateTime uploadedAt = LocalDateTime.now();

    // Getters and Setters
    // ... [Previous getters and setters]
}
```

### 2. Controllers

#### AuthController (`Controller/AuthController.java`)
```java
package com.nexus.nexusproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nexus.nexusproject.DTO.AuthResponse;
import com.nexus.nexusproject.DTO.LoginRequest;
import com.nexus.nexusproject.DTO.SignUpRequest;
import com.nexus.nexusproject.Service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest signupRequest) {
        AuthResponse response = userService.signup(signupRequest);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response = userService.login(loginRequest);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth API is working!");
    }
}
```

#### ResourceController (`Controller/ResourceController.java`)
```java
package com.nexus.nexusproject.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.nexus.nexusproject.Service.ResourceService;
import com.nexus.nexusproject.model.ExamResource;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "*")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam String subjectCode,
                                    @RequestParam String subjectName,
                                    @RequestParam String professorName,
                                    @RequestParam String type,      // Mid Sem / End Sem
                                    @RequestParam Integer semester, // 1-8
                                    @RequestParam Integer year,
                                    @RequestParam String branch) {
        try {
            ExamResource saved = resourceService.uploadResource(file, subjectCode, subjectName, 
                                                             professorName, type, semester, year, branch);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ExamResource>> fetch(@RequestParam(required = false) Integer semester,
                                                  @RequestParam(required = false) String branch,
                                                  @RequestParam(required = false) String type) {
        return ResponseEntity.ok(resourceService.fetchResources(semester, branch, type));
    }
}
```

### 3. Services

#### ResourceService (`Service/ResourceService.java`)
```java
package com.nexus.nexusproject.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nexus.nexusproject.Repository.ExamResourceRepository;
import com.nexus.nexusproject.model.ExamResource;

@Service
public class ResourceService {

    private final Cloudinary cloudinary;
    private final ExamResourceRepository repository;

    @Autowired
    public ResourceService(Cloudinary cloudinary, ExamResourceRepository repository) {
        this.cloudinary = cloudinary;
        this.repository = repository;
    }

    public ExamResource uploadResource(MultipartFile file,
                                     String subjectCode,
                                     String subjectName,
                                     String professorName,
                                     String type,
                                     Integer semester,
                                     Integer year,
                                     String branch) throws IOException {

        // Upload the file to Cloudinary (as "raw" for PDFs)
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "raw",
                        "folder", "exam-vault"
                )
        );

        String fileUrl = String.valueOf(uploadResult.get("secure_url"));

        // Save metadata to Postgres
        ExamResource resource = new ExamResource();
        resource.setSubjectCode(subjectCode != null ? subjectCode.trim() : null);
        resource.setSubjectName(subjectName != null ? subjectName.trim() : null);
        resource.setProfessorName(professorName != null ? professorName.trim() : null);
        resource.setType(type);
        resource.setSemester(semester);
        resource.setYear(year);
        resource.setBranch(branch);
        resource.setFileUrl(fileUrl);

        return repository.save(resource);
    }

    public List<ExamResource> fetchResources(Integer semester, String branch, String type) {
        // Default: Sem 1, CSE, Mid Sem
        int sem = (semester != null) ? semester : 1;
        String br = (branch != null) ? branch : "CSE";
        String tp = (type != null) ? type : "Mid Sem";
        return repository.findBySemesterAndBranchAndType(sem, br, tp);
    }
}
```

### 4. DTOs (Data Transfer Objects)

#### AuthResponse
```java
package com.nexus.nexusproject.DTO;

public class AuthResponse {
    private String token;
    private String message;
    private boolean success;
    
    // Getters and setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
```

#### LoginRequest
```java
package com.nexus.nexusproject.DTO;

public class LoginRequest {
    private String username;
    private String password;
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
```

#### SignUpRequest
```java
package com.nexus.nexusproject.DTO;

public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    
    // Getters and setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
```

### 5. Repositories

#### UserRepository
```java
package com.nexus.nexusproject.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nexus.nexusproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
```

#### ExamResourceRepository
```java
package com.nexus.nexusproject.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nexus.nexusproject.model.ExamResource;

public interface ExamResourceRepository extends JpaRepository<ExamResource, Long> {
    List<ExamResource> findBySemesterAndBranchAndType(Integer semester, String branch, String type);
}
```

## Configuration

### Application Properties (`application.properties`)
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/nexus_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Cloudinary Configuration
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### Cloudinary Configuration
```java
package com.nexus.nexusproject.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }
}
```

## API Endpoints

### Authentication
- `POST /api/auth/signup`: Register new user
- `POST /api/auth/login`: Authenticate user
- `GET /api/auth/test`: Test authentication endpoint

### Resources
- `POST /api/resources/upload`: Upload new resource with metadata
- `GET /api/resources`: Get resources with optional filters (semester, branch, type)

## Build and Run
1. Ensure PostgreSQL is running
2. Configure `application.properties`
3. Run `mvn clean install`
4. Run `mvn spring-boot:run`

## Technologies Used
- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- PostgreSQL
- Cloudinary SDK
- Spring Validation

## Dependencies
```xml
<dependencies>
    <!-- Web & JSON -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- JPA & PostgreSQL -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Cloudinary SDK -->
    <dependency>
        <groupId>com.cloudinary</groupId>
        <artifactId>cloudinary-http44</artifactId>
        <version>1.37.0</version>
    </dependency>

    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

## Core Components

### 1. Models

#### User Model
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors and Getters/Setters
}
```

#### ExamResource Model
```java
@Entity
public class ExamResource {
    // Fields for exam resources
    private Long id;
    private String title;
    private String description;
    private String fileUrl;
    private String fileType;
    // Additional fields and methods
}
```

### 2. Controllers

#### AuthController
Handles user authentication and registration endpoints:
- POST /api/auth/signup
- POST /api/auth/login

#### ResourceController
Manages exam resource operations:
- GET /api/resources
- POST /api/resources
- GET /api/resources/{id}
- DELETE /api/resources/{id}

### 3. DTOs (Data Transfer Objects)

#### AuthResponse
```java
public class AuthResponse {
    private String token;
    private String message;
    // Getters and setters
}
```

#### LoginRequest
```java
public class LoginRequest {
    private String username;
    private String password;
    // Getters and setters
}
```

#### SignUpRequest
```java
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    // Getters and setters
}
```

### 4. Repositories

#### UserRepository
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
```

#### ExamResourceRepository
```java
public interface ExamResourceRepository extends JpaRepository<ExamResource, Long> {
    List<ExamResource> findByUserId(Long userId);
}
```

### 5. Services

#### ResourceService
Handles business logic for exam resource management:
- File upload to Cloudinary
- Resource CRUD operations
- User-specific resource management

## Configuration

### Application Properties
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/nexus_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Cloudinary Configuration
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## Security Configuration
- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control

## API Endpoints

### Authentication
- `POST /api/auth/signup`: Register new user
- `POST /api/auth/login`: Authenticate user

### Resources
- `GET /api/resources`: Get all resources
- `POST /api/resources`: Upload new resource
- `GET /api/resources/{id}`: Get specific resource
- `DELETE /api/resources/{id}`: Delete resource

## Build and Run
1. Ensure PostgreSQL is running
2. Configure application.properties
3. Run `mvn clean install`
4. Run `mvn spring-boot:run`

## Testing
- Unit tests using JUnit and Mockito
- Integration tests for controllers and services
- Repository tests with test database
