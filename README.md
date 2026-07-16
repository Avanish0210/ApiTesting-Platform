<div align="center">

# 🚀 ApiTesting Platform

<img src="https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen?style=for-the-badge&logo=spring-boot" alt="Spring Boot">
<img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" alt="Java 21">
<img src="https://img.shields.io/badge/PostgreSQL-Latest-blue?style=for-the-badge&logo=postgresql" alt="PostgreSQL">
<img src="https://img.shields.io/badge/REST%20API-Tested-success?style=for-the-badge" alt="REST API">

**Enterprise-Grade API Testing & Automation Platform** ⚡

> A powerful, feature-rich Spring Boot application for comprehensive API testing, automation, and reporting with enterprise-level capabilities.

[Features](#-features) • [Getting Started](#-getting-started) • [Architecture](#-architecture) • [API Endpoints](#-api-endpoints) • [Contributing](#-contributing)

---

</div>

## ✨ Features

### 🧪 Core Testing Capabilities

#### 1. **API Testing** 
```
POST /api/test
```
- Execute single API requests with full control
- Support for multiple HTTP methods (GET, POST, PUT, DELETE, PATCH, etc.)
- Request/response validation in real-time
- Detailed response analysis and metrics
- ⚡ Real-time execution feedback

#### 2. **Request Collections**
```
GET/POST/PUT/DELETE /api/collections
```
- Organize API requests into logical collections
- Manage multiple collections for different projects
- Collection versioning and metadata
- Share collections across team members
- 📦 Hierarchical organization support

#### 3. **Collection Runner**
```
POST /api/collection-runner/{collectionId}/run
```
- Execute entire collections of API requests sequentially
- Parallel execution capabilities
- Request dependency management
- Pass data between requests
- 🔄 Automated workflow execution

#### 4. **Saved Requests**
```
GET/POST/PUT/DELETE /api/saved-requests
```
- Save frequently used API requests
- Quick access to templates
- Request library management
- Tagging and categorization
- 💾 One-click request execution

---

### 🌍 Environment Management

#### 5. **Environments**
```
GET/POST/PUT/DELETE /api/environments
```
- Create and manage multiple environments (DEV, QA, PROD, STAGING)
- Environment-specific variables and configurations
- Easy switching between environments
- Variable substitution in requests
- 🔐 Secure credential management

#### 6. **Environment Variables**
- Dynamic variable resolution
- Runtime variable injection
- Global and environment-scoped variables
- Support for complex variable hierarchies
- 📊 Real-time variable tracking

---

### 📊 Data-Driven Testing

#### 7. **Data-Driven Execution**
```
POST /api/data-driven/{requestId}
```
- Execute API requests with CSV/dataset parameters
- Parameterized test execution
- Mass testing with multiple datasets
- Variable iteration and substitution
- 📈 Bulk testing capabilities

#### 8. **Dataset Support**
- CSV file upload and parsing
- Excel dataset support
- Custom delimiter configurations
- Row-by-row execution tracking
- 🎯 Granular data iteration

---

### 🔍 Response Analysis & Validation

#### 9. **Assertion Engine**
```
Multiple Assertion Types:
- Equals (value comparison)
- Contains (substring matching)
- Regex (pattern matching)
- GreaterThan / LessThan (numeric comparison)
- Type checking (JSON, XML, etc.)
- StatusCode validation
```
- Complex assertion chains
- Conditional assertions
- Custom assertion logic
- 🎯 Comprehensive validation framework

#### 10. **Extraction Rules**
```
POST /api/extraction-rules/{requestId}
```
- Extract data from responses using JSONPath
- Store extracted values as variables
- Multi-level extraction support
- Chained data extraction
- 🔗 Variable reuse across requests

#### 11. **Response Parsing**
- JSON response analysis
- XML response handling
- Dynamic value extraction
- Response schema validation
- 📋 Advanced parsing capabilities

---

### 📅 Scheduling & Automation

#### 12. **Test Scheduling**
```
POST/PUT/GET/DELETE /api/schedules
```
- Cron-based scheduling
- Recurring test execution
- One-time scheduled runs
- Complex scheduling patterns
- ⏰ Automated test execution on schedule

#### 13. **Execution History**
```
GET /api/execution-history
```
- Track all test executions
- Detailed execution logs
- Historical trend analysis
- Performance metrics over time
- 📜 Complete audit trail

#### 14. **History Cleanup**
- Automatic old execution cleanup
- Configurable retention policies
- Manual cleanup options
- Archive old executions
- 🧹 Automatic maintenance

---

### 🔔 Notifications & Reporting

#### 15. **Email Notifications**
```
POST /api/notifications
```
- Automatic email alerts on test failures
- Custom notification templates
- Multiple recipient support
- Scheduled digest emails
- 📧 Rich email formatting

#### 16. **Notification Configuration**
```
POST/PUT/GET/DELETE /api/notifications
```
- Configure notification rules
- Filter notifications by status
- Custom notification triggers
- SMTP integration
- ⚙️ Flexible notification settings

#### 17. **Email Templates**
- HTML-based templates
- Dynamic content injection
- Test result summarization
- Custom branding options
- 🎨 Professional email formatting

---

### 📄 Report Generation

#### 18. **PDF Reports**
```
GET /api/reports/runs/{runId}/pdf
```
- Generate comprehensive PDF reports
- Visual test results
- Charts and graphs
- Execution timeline
- 📕 Professional documentation

#### 19. **Excel Reports**
```
GET /api/reports/runs/{runId}/excel
```
- Export results to Excel format
- Spreadsheet analytics
- Data pivot tables
- Custom formatting
- 📗 Business intelligence ready

#### 20. **Dashboard Analytics**
```
GET /api/dashboard/overview
```
- Real-time test analytics
- Success/failure metrics
- Performance statistics
- Trend analysis
- 📊 Executive summary view

---

### 🔄 Integration & Interoperability

#### 21. **Postman Integration**
```
POST /api/postman/import
GET /api/postman/export/{collectionId}
```

**Import Postman Collections:**
- Convert Postman JSON to collections
- Automatic request migration
- Environment variable mapping
- Seamless workflow integration

**Export Collections:**
- Export to Postman format
- Share with team via Postman
- Maintain compatibility
- 🔁 Bidirectional sync support

#### 22. **Collection Analytics**
```
GET /api/collections/{id}/analytics
```
- Collection performance metrics
- Request success rates
- Average response times
- Failure analysis
- 📈 In-depth collection insights

---

### 🔐 Security & Authentication

#### 23. **Authentication Support**
```
Supported Auth Types:
- No Auth
- API Key (Header, Query Parameter, Cookie)
- Bearer Token
- Basic Authentication
- OAuth 2.0 (configurable)
```
- Secure credential storage
- Token management
- Session handling
- 🔒 Enterprise-grade security

#### 24. **Request Security**
- HTTPS/SSL support
- Cookie management
- Header security
- Request encryption options
- 🛡️ Data protection

---

### 📝 Request Building

#### 25. **Request Configuration**
```
Supported Components:
- HTTP Headers (custom, pre-defined)
- Query Parameters
- Request Body (JSON, XML, Form Data, Raw)
- Form Data & File Upload
- Cookies
- Authentication
```
- Drag-and-drop interface ready
- Template-based request building
- Preset configurations
- 🛠️ Complete request customization

#### 26. **Body Types**
- **JSON**: Full JSON request bodies
- **XML**: XML document support
- **Form Data**: Multipart form data
- **Raw**: Custom raw content
- **Binary**: File uploads
- 📦 Multiple content-type support

---

### 🎯 Advanced Features

#### 27. **Dynamic Variables**
- Variable substitution in requests
- Runtime value injection
- Cross-request data passing
- Conditional variables
- 🔀 Dynamic workflow orchestration

#### 28. **Request Builder Service**
- Intelligent request construction
- Pre-validation checks
- Automatic formatting
- 🏗️ Robust request assembly

#### 29. **Variable Extraction Service**
- JSONPath expression support
- Regex-based extraction
- XML/HTML parsing
- Multi-value extraction
- 🔍 Intelligent data extraction

#### 30. **Response Builder Service**
- Response normalization
- Error handling
- Response metadata enrichment
- 📦 Structured response formatting

---

## 🏗️ Architecture

### Tech Stack
```
┌─────────────────────────────────────┐
│       Spring Boot 4.1.0             │
│  ┌───────────────────────────────┐  │
│  │   REST Controllers            │  │
│  │   (13 specialized modules)    │  │
│  └───────────────────────────────┘  │
│  ┌───────────────────────────────┐  │
│  │   Service Layer (50+ services)│  │
│  │   (Business Logic)            │  │
│  └───────────────────────────────┘  │
│  ┌───────────────────────────────┐  │
│  │   Data Access Layer (JPA)     │  │
│  │   (Repository Pattern)        │  │
│  └───────────────────────────────┘  │
│  ┌───────────────────────────────┐  │
│  │   PostgreSQL Database         │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

### Project Structure
```
src/main/java/com/example/ApiTesting/
├── controller/          # REST API endpoints (13 controllers)
├── service/             # Business logic (50+ services)
├── entity/              # Database entities (24+ entities)
├── dto/                 # Data transfer objects (40+ DTOs)
├── repository/          # Data access layer
├── mapper/              # Entity-DTO mapping
├── exception/           # Custom exceptions
├── scheduler/           # Scheduled tasks
├── utility/             # Helper utilities
├── specification/       # JPA specifications
└── config/              # Configuration classes
```

---

## 🚀 Getting Started

### Prerequisites
```bash
- Java 21 or higher
- Maven 3.8+
- PostgreSQL 12+
- Git
```

### Installation

#### 1. Clone the Repository
```bash
git clone <repository-url>
cd ApiTesting
```

#### 2. Configure Database
```bash
# Edit application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/api_testing
spring.datasource.username=postgres
spring.datasource.password=your_password
```

#### 3. Build the Application
```bash
# Using Maven
./mvnw clean package

# Or on Windows
mvnw.cmd clean package
```

#### 4. Run the Application
```bash
# Using Maven
./mvnw spring-boot:run

# Or run the JAR
java -jar target/ApiTesting-0.0.1-SNAPSHOT.jar
```

#### 5. Access the Application
```
API Base URL: http://localhost:8080
```

---

## 📡 API Endpoints Overview

### Core Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/api/test` | Execute a single API test |
| **GET** | `/api/collections` | Get all collections |
| **POST** | `/api/collections` | Create new collection |
| **GET** | `/api/collections/{id}` | Get collection by ID |
| **PUT** | `/api/collections/{id}` | Update collection |
| **DELETE** | `/api/collections/{id}` | Delete collection |

### Execution & Automation

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/api/collection-runner/{id}/run` | Run collection tests |
| **POST** | `/api/data-driven/{requestId}` | Execute data-driven tests |
| **GET** | `/api/execution-history` | Get execution history |
| **POST** | `/api/schedules` | Create test schedule |
| **GET** | `/api/schedules/{id}` | Get schedule details |

### Reporting & Analytics

| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/api/reports/runs/{runId}/pdf` | Generate PDF report |
| **GET** | `/api/reports/runs/{runId}/excel` | Generate Excel report |
| **GET** | `/api/dashboard/overview` | Get dashboard analytics |
| **GET** | `/api/collections/{id}/analytics` | Collection analytics |

### Integration

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/api/postman/import` | Import Postman collection |
| **GET** | `/api/postman/export/{id}` | Export to Postman format |
| **GET** | `/api/environments` | Get environments |
| **POST** | `/api/environments` | Create environment |

### Configuration

| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/api/extraction-rules/{id}` | Create extraction rule |
| **POST** | `/api/notifications` | Configure notifications |
| **GET** | `/api/saved-requests` | Get saved requests |
| **POST** | `/api/saved-requests` | Save request |

---

## 📚 Usage Examples

### Example 1: Create and Run a Collection

```bash
# Step 1: Create a Collection
curl -X POST http://localhost:8080/api/collections \
  -H "Content-Type: application/json" \
  -d '{
    "name": "User API Tests",
    "description": "Tests for user management endpoints"
  }'

# Response: Collection ID = abc123

# Step 2: Add Requests to Collection
# (Use collection runner endpoints)

# Step 3: Run the Collection
curl -X POST http://localhost:8080/api/collection-runner/abc123/run \
  -H "Content-Type: application/json"
```

### Example 2: Data-Driven Testing

```bash
# Upload CSV file and execute data-driven tests
curl -X POST http://localhost:8080/api/data-driven/request-id \
  -F "file=@test-data.csv"
```

### Example 3: Generate Report

```bash
# Generate PDF Report
curl -X GET http://localhost:8080/api/reports/runs/run-id/pdf \
  -o test-report.pdf
```

### Example 4: Import Postman Collection

```bash
curl -X POST http://localhost:8080/api/postman/import \
  -F "file=@postman-collection.json"
```

---

## 🎛️ Configuration

### Application Properties

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/api_testing
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Logging
logging.level.root=INFO
logging.level.com.example.ApiTesting=DEBUG
```

---

## 🔌 Dependencies

### Core Dependencies
```xml
<!-- Spring Boot -->
spring-boot-starter-webmvc
spring-boot-starter-data-jpa
spring-boot-starter-validation
spring-boot-starter-mail

<!-- Database -->
postgresql

<!-- JSON Processing -->
jsonpath 2.9.0

<!-- Data Processing -->
apache-commons-csv 1.14.1
poi-ooxml 5.4.1

<!-- PDF Generation -->
openhtmltopdf-pdfbox 1.0.10

<!-- Utilities -->
lombok
```

---

## 🛠️ Development

### Running Tests
```bash
./mvnw test
```

### Building for Production
```bash
./mvnw clean package -DskipTests
```

### Docker Support
```dockerfile
FROM openjdk:21-slim
COPY target/ApiTesting-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

---

## 📊 Key Components

### Controllers (13 Modules)
- **ApiTestController** - Single API test execution
- **CollectionController** - Collection management
- **CollectionRunnerController** - Collection execution
- **DataDrivenController** - Data-driven testing
- **EnvironmentController** - Environment management
- **SavedRequestController** - Request templates
- **ExtractionRuleController** - Response data extraction
- **ScheduleController** - Test scheduling
- **NotificationConfigController** - Alert configuration
- **PostmanController** - Postman integration
- **ReportController** - Report generation (PDF/Excel)
- **ExecutionHistoryController** - History tracking
- **DashboardController** - Analytics & insights

### Services (50+ Implementation Classes)
- Request execution and validation
- Collection and environment management
- Data-driven test orchestration
- Report generation (PDF/Excel)
- Email notification handling
- Postman import/export
- Variable extraction and resolution
- Scheduling and execution tracking
- Dashboard analytics

### Entities (24+ Data Models)
- Collection, SavedRequest, Environment
- ExecutionHistory, CollectionRun
- RequestAssertion, ExtractionRule
- Schedule, NotificationConfig
- And many more...

---

## 🎯 Use Cases

### 1. **Regression Testing**
- Execute full test suites automatically
- Track test results over time
- Generate quality metrics

### 2. **CI/CD Integration**
- Trigger tests from CI/CD pipelines
- Automatic reporting
- Email notifications on failures

### 3. **Load Testing**
- Data-driven testing for volume scenarios
- Performance metrics collection
- Trend analysis

### 4. **API Monitoring**
- Scheduled test execution
- Uptime monitoring
- Health check automation

### 5. **Team Collaboration**
- Shared collection repositories
- Environment-specific testing
- Centralized result tracking

---

## 🚀 Performance Features

- ⚡ Asynchronous request execution
- 🔄 Parallel test execution support
- 📦 Efficient data extraction (JSONPath)
- 💾 Smart caching mechanisms
- 🗑️ Automatic history cleanup
- 📊 Optimized database queries

---

## 🔒 Security Features

- 🔐 Multiple authentication methods
- 🛡️ Secure credential storage
- 🔒 HTTPS/SSL support
- 🚪 Request validation
- 📋 Audit logging
- 🔑 Token management

---

## 📈 Scalability

- **Horizontal Scaling**: Stateless REST API
- **Database Optimization**: Indexed queries, JPA specifications
- **Async Processing**: Background job support
- **Connection Pooling**: Optimized database connections

---

## 🤝 Contributing

### Steps to Contribute

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow Spring Boot conventions
- Use Lombok for boilerplate reduction
- Write unit tests for new features
- Document complex logic

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🆘 Support & Documentation

### Getting Help
- 📖 Check the [HELP.md](HELP.md) for setup guidance
- 🐛 Report issues on GitHub Issues
- 💬 Start discussions for questions

### Additional Resources
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [REST API Best Practices](https://restfulapi.net/)

---

## 🌟 Key Highlights

| Feature | Benefit |
|---------|---------|
| **30+ Features** | Comprehensive testing platform |
| **Easy Integration** | Postman, Environments, Variables |
| **Automated Reporting** | PDF & Excel export |
| **Scheduling** | Cron-based automation |
| **Data-Driven** | Parameterized testing |
| **Email Alerts** | Real-time notifications |
| **Analytics** | Performance insights |
| **Scalable** | Enterprise-ready |

---

<div align="center">

### 🎉 Built with ❤️ using Spring Boot

**[⬆ Back to Top](#-apitesting-platform)**

---

*Last Updated: 2026* | *Version: 0.0.1*

</div>
