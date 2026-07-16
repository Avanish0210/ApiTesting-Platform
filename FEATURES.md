# 🚀 ApiTesting Platform - Complete Features Guide

> A comprehensive reference for all features available in the ApiTesting platform.

---

## Table of Contents

1. [Testing Features](#-testing-features)
2. [Collection Management](#-collection-management)
3. [Environment Management](#-environment-management)
4. [Data-Driven Testing](#-data-driven-testing)
5. [Assertions & Validation](#-assertions--validation)
6. [Scheduling & Automation](#-scheduling--automation)
7. [Notifications](#-notifications)
8. [Reporting](#-reporting)
9. [Integration Features](#-integration-features)
10. [Advanced Features](#-advanced-features)

---

## 🧪 Testing Features

### Single API Testing
**Endpoint**: `POST /api/test`

Execute individual API requests with comprehensive control.

**Capabilities**:
- ✅ HTTP Methods: GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS
- ✅ Custom headers support
- ✅ Query parameters
- ✅ Request body (JSON, XML, Form Data, Raw)
- ✅ File uploads
- ✅ Authentication (Basic, Bearer, API Key, OAuth)
- ✅ Cookie management
- ✅ SSL/TLS certificate handling
- ✅ Request timeout configuration
- ✅ Follow redirects
- ✅ Request/response logging
- ✅ Performance metrics

**Request Example**:
```json
{
  "url": "https://api.example.com/users",
  "method": "GET",
  "headers": {
    "Authorization": "Bearer token123",
    "Content-Type": "application/json"
  },
  "queryParameters": {
    "page": "1",
    "limit": "10"
  }
}
```

**Response Includes**:
- Status code
- Response headers
- Response body (pretty-printed)
- Response time (ms)
- Response size (bytes)
- Validation results

---

### Request Presets
- Pre-configured common requests
- Quick templates
- Environment-specific URLs
- Default headers and authentication

---

## 📦 Collection Management

### Create Collections
Organize related API requests into logical groups.

**Features**:
- ✅ Hierarchical organization
- ✅ Collection metadata (name, description, tags)
- ✅ Collection versioning
- ✅ Access control
- ✅ Collection cloning
- ✅ Bulk operations
- ✅ Collection templates

### Manage Requests in Collections
- Add/remove requests
- Reorder requests
- Request dependencies
- Request prerequisites
- Post-request scripts

### Collection Runner
**Endpoint**: `POST /api/collection-runner/{collectionId}/run`

Execute all requests in a collection sequentially or in parallel.

**Features**:
- ✅ Sequential execution
- ✅ Parallel execution
- ✅ Request ordering
- ✅ Data passing between requests
- ✅ Conditional execution
- ✅ Loop support
- ✅ Execution reporting
- ✅ Stop on error option
- ✅ Timeout handling

**Execution Report Includes**:
- Total requests executed
- Passed/failed count
- Execution time
- Per-request details
- Error logs

---

## 🌍 Environment Management

### Environment Setup
**Endpoint**: `GET/POST/PUT/DELETE /api/environments`

Create and manage environment-specific configurations.

**Environment Types**:
- Development (DEV)
- Testing (QA/TEST)
- Staging (STAGING)
- Production (PROD)
- Custom environments

### Environment Variables
**Features**:
- ✅ Global variables
- ✅ Environment-scoped variables
- ✅ Variable substitution in requests
- ✅ Variable types (string, number, boolean, object)
- ✅ Secure variables (encrypted)
- ✅ Variable templates
- ✅ Variable inheritance
- ✅ Environment switching

**Variable Substitution Syntax**:
```
{{variable_name}}
```

**Example**:
```
URL: {{base_url}}/api/users
Header: Authorization: Bearer {{access_token}}
Body: { "email": "{{test_email}}" }
```

### Variable Types
```
String:     {{string_var}}
Number:     {{port}}
Boolean:    {{debug_mode}}
Object:     {{user_data}}
Array:      {{endpoints}}
```

---

## 📊 Data-Driven Testing

### CSV Dataset Support
**Endpoint**: `POST /api/data-driven/{requestId}`

Execute API requests with data from external files.

**Supported Formats**:
- ✅ CSV (comma-separated)
- ✅ TSV (tab-separated)
- ✅ Custom delimiters
- ✅ Headers from first row
- ✅ Excel (XLSX)

**Dataset Example** (test-data.csv):
```csv
username,email,password
john_doe,john@example.com,pass123
jane_smith,jane@example.com,pass456
bob_jones,bob@example.com,pass789
```

### Execution Flow
1. Upload dataset file
2. Map columns to request variables
3. Execute request for each row
4. Aggregate results
5. Generate report

### Features
- ✅ Row-by-row execution
- ✅ Variable mapping
- ✅ Error handling per row
- ✅ Continue on error
- ✅ Partial failure handling
- ✅ Execution statistics
- ✅ Data validation before execution

### Execution Report
- Total rows processed
- Successful executions
- Failed executions
- Error details per row
- Aggregate metrics

---

## 🔍 Assertions & Validation

### Assertion Types

#### 1. **Status Code Assertion**
```json
{
  "type": "StatusCode",
  "operator": "Equals",
  "expectedValue": 200
}
```

#### 2. **Response Body Assertion**
```json
{
  "type": "ResponseBody",
  "operator": "Contains",
  "expectedValue": "success"
}
```

#### 3. **JSON Path Assertion**
```json
{
  "type": "JSONPath",
  "path": "$.data.user.id",
  "operator": "GreaterThan",
  "expectedValue": 0
}
```

#### 4. **Response Time Assertion**
```json
{
  "type": "ResponseTime",
  "operator": "LessThan",
  "expectedValue": 1000  // milliseconds
}
```

#### 5. **Header Assertion**
```json
{
  "type": "Header",
  "headerName": "Content-Type",
  "operator": "Contains",
  "expectedValue": "application/json"
}
```

#### 6. **Regex Assertion**
```json
{
  "type": "Regex",
  "pattern": "^[0-9]{10}$",
  "operator": "Matches",
  "value": "response_text"
}
```

### Assertion Operators
- `Equals` - Exact match
- `NotEquals` - Not equal
- `Contains` - Substring match
- `NotContains` - Doesn't contain
- `GreaterThan` - Numeric comparison
- `LessThan` - Numeric comparison
- `GreaterThanOrEqual` - Numeric comparison
- `LessThanOrEqual` - Numeric comparison
- `Matches` - Regex pattern match
- `NotMatches` - Regex not match
- `Exists` - Value exists
- `NotExists` - Value doesn't exist
- `StartsWith` - String starts with
- `EndsWith` - String ends with

### Multiple Assertions
Chain multiple assertions for comprehensive validation:

```json
{
  "assertions": [
    {"type": "StatusCode", "operator": "Equals", "expectedValue": 200},
    {"type": "JSONPath", "path": "$.success", "operator": "Equals", "expectedValue": true},
    {"type": "ResponseTime", "operator": "LessThan", "expectedValue": 1000},
    {"type": "Header", "headerName": "Content-Type", "operator": "Contains", "expectedValue": "json"}
  ]
}
```

---

## 🔗 Extraction Rules

### Data Extraction
**Endpoint**: `POST /api/extraction-rules/{requestId}`

Extract data from responses and store as variables for reuse.

### Extraction Methods
- ✅ JSONPath expressions
- ✅ XPath for XML responses
- ✅ Regex patterns
- ✅ Header value extraction
- ✅ Full body capture
- ✅ Cookie value extraction

### JSONPath Examples
```
$.data.user.id              // Extract user ID
$.data.items[0].name        // Extract first item name
$..email                    // Extract all emails (recursive)
$.status                    // Extract status field
```

### Extraction Configuration
```json
{
  "name": "user_id",
  "extractionType": "JSONPath",
  "path": "$.data.user.id",
  "storeAs": "VARIABLE"
}
```

### Extracted Data Usage
```
Subsequent requests can reference:
{{user_id}}
{{extracted_email}}
{{auth_token}}
```

---

## 📅 Scheduling & Automation

### Schedule Creation
**Endpoint**: `POST /api/schedules`

Schedule test collections to run automatically.

### Schedule Types

#### 1. **Cron-based Scheduling**
```
0 0 * * * MON    // Every Monday at midnight
0 */6 * * *      // Every 6 hours
0 9-17 * * MON-FRI  // Every hour from 9-5, Mon-Fri
*/15 * * * *     // Every 15 minutes
```

#### 2. **Simple Intervals**
- Every hour
- Every 4 hours
- Every day
- Every week
- Every month

#### 3. **One-time Execution**
- Schedule for specific date/time
- Ad-hoc execution
- Manual trigger

### Schedule Configuration
```json
{
  "collectionId": "uuid",
  "scheduleType": "CRON",
  "cronExpression": "0 0 * * MON",
  "enabled": true,
  "timezone": "UTC",
  "notifyOnFailure": true,
  "notifyOnSuccess": false
}
```

### Execution Tracking
**Endpoint**: `GET /api/execution-history`

Track scheduled execution history.

**Features**:
- ✅ Execution timestamp
- ✅ Execution status
- ✅ Result summary
- ✅ Error logs
- ✅ Performance metrics
- ✅ Execution duration

---

## 🔔 Notifications

### Email Notifications
**Endpoint**: `POST /api/notifications`

Configure automated email alerts.

### Notification Triggers
- ✅ Test execution failure
- ✅ Collection run completion
- ✅ Scheduled test results
- ✅ Performance threshold exceeded
- ✅ Custom triggers

### Notification Configuration
```json
{
  "name": "API Test Failures",
  "type": "EMAIL",
  "recipients": ["team@example.com", "dev@example.com"],
  "triggers": ["TEST_FAILURE"],
  "enabled": true,
  "emailTemplate": "DEFAULT"
}
```

### Email Templates
- Test failure report
- Collection completion summary
- Performance alert
- Daily digest
- Weekly report
- Custom HTML templates

### Template Variables
```
{{test_name}}
{{test_status}}
{{failure_reason}}
{{execution_time}}
{{timestamp}}
{{collection_name}}
{{environment_name}}
{{error_details}}
```

---

## 📄 Reporting

### PDF Report Generation
**Endpoint**: `GET /api/reports/runs/{runId}/pdf`

Generate professional PDF reports.

**Report Contents**:
- ✅ Executive summary
- ✅ Test statistics
- ✅ Pass/fail breakdown
- ✅ Detailed test results
- ✅ Performance metrics
- ✅ Response samples
- ✅ Error details
- ✅ Execution timeline
- ✅ Charts and graphs
- ✅ Timestamps

### Excel Report Generation
**Endpoint**: `GET /api/reports/runs/{runId}/excel`

Export test results to Excel format.

**Features**:
- ✅ Multiple worksheets
- ✅ Formatted tables
- ✅ Charts and pivots
- ✅ Summary statistics
- ✅ Detailed results
- ✅ Custom formatting
- ✅ Export filters

### Dashboard Analytics
**Endpoint**: `GET /api/dashboard/overview`

Real-time analytics and insights.

**Dashboard Metrics**:
- ✅ Total tests executed
- ✅ Success rate (%)
- ✅ Average response time
- ✅ Failure trends
- ✅ Most failed endpoints
- ✅ Performance over time
- ✅ Recent executions
- ✅ Active schedules

---

## 🔄 Integration Features

### Postman Integration
**Import**: `POST /api/postman/import`
**Export**: `GET /api/postman/export/{collectionId}`

#### Import Postman Collections
- ✅ Parse Postman JSON format
- ✅ Convert requests to platform format
- ✅ Migrate environments
- ✅ Preserve request metadata
- ✅ Auto-map variables
- ✅ Handle nested folders
- ✅ Support pre/post scripts

#### Export to Postman
- ✅ Generate valid Postman collection JSON
- ✅ Include environments
- ✅ Export variables
- ✅ Maintain request structure
- ✅ Share with team

### Environment Integration
- ✅ Environment switching
- ✅ Variable inheritance
- ✅ Multi-environment testing
- ✅ Environment templates

---

## 🛠️ Advanced Features

### Request Builder Service
- Intelligent request construction
- Pre-validation
- Automatic formatting
- Template expansion
- Variable substitution

### Response Builder Service
- Response normalization
- Error handling
- Metadata enrichment
- Format conversion
- Pretty printing

### Dynamic Variable Service
- Runtime variable resolution
- Complex expressions
- Conditional variables
- Function-based variables

### Variable Extraction Service
- JSONPath evaluation
- XPath support
- Regex extraction
- Multi-value extraction
- Type inference

### Data-Driven Variable Resolver
- CSV to variable mapping
- Row iteration
- Column selection
- Type conversion
- Validation

### Environment Resolver Service
- Environment switching
- Variable priority resolution
- Inheritance chain
- Default value fallback
- Scope handling

### Execution History Cleanup
- Automatic old record removal
- Configurable retention
- Archive options
- Performance optimization

### Collection Analytics Service
- Performance metrics
- Success rates
- Response time analysis
- Failure patterns
- Trend analysis

---

## 🔐 Security Features

### Authentication Methods
1. **No Authentication**
2. **API Key**
   - Header location
   - Query parameter location
   - Cookie location
3. **Bearer Token**
4. **Basic Authentication**
   - Username/password encoding
5. **OAuth 2.0**
   - Token management
   - Refresh token support

### Secure Storage
- ✅ Encrypted credentials
- ✅ Secure variable storage
- ✅ Token encryption
- ✅ Access control
- ✅ Audit logging

### Request Security
- ✅ HTTPS/SSL support
- ✅ Certificate validation
- ✅ Cookie handling
- ✅ Header security
- ✅ Body encryption options

---

## 📊 Analytics & Insights

### Performance Metrics
- Response time distribution
- Throughput (requests/sec)
- Error rate trends
- Availability percentage
- Success rate trends

### Failure Analysis
- Most common errors
- Failed endpoints
- Error time distribution
- Root cause analysis
- Retry success rate

### Trend Analysis
- Historical performance
- Success rate trends
- Response time trends
- Failure frequency
- Pattern detection

---

## 🎯 Use Case Examples

### 1. Continuous API Testing
```
Schedule → Collection Run → Assertions → Reports → Notifications
```

### 2. Regression Testing
```
Import Postman → Save Collection → Data-Driven Testing → PDF Report
```

### 3. Load Testing
```
Large CSV Dataset → Data-Driven Execution → Performance Analysis
```

### 4. Integration Testing
```
Environment Setup → Multiple Collections → Dependency Handling → Results
```

### 5. API Monitoring
```
Schedule + Alerts → Email Notifications → Dashboard Tracking
```

---

## 📈 Performance Capabilities

- **Concurrent Requests**: Parallel execution support
- **Large Datasets**: Handle 1000s of rows
- **Report Generation**: PDF/Excel creation
- **Execution Speed**: Sub-second API calls
- **Storage**: Efficient history management
- **Scalability**: Horizontal scaling ready

---

## 🚀 Best Practices

1. **Use Environments** for different configurations
2. **Extract Variables** for data reuse
3. **Create Assertions** for validation
4. **Schedule Tests** for automation
5. **Use Collections** for organization
6. **Configure Notifications** for alerts
7. **Generate Reports** for documentation
8. **Archive History** for maintenance
9. **Version Collections** for tracking
10. **Test in Sequence** when dependent

---

**Last Updated**: 2026 | **Version**: 0.0.1

