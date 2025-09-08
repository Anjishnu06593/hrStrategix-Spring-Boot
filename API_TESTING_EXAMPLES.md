# HR Strategix API Testing Examples

## Authentication

First, login to get an access token:

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "identifier": "admin",
    "password": "password"
  }'
```

Use the returned `accessToken` in the Authorization header for subsequent requests:
```
Authorization: Bearer <your-access-token>
```

## Employee Management APIs

### 1. Get All Employees
```bash
curl -X GET http://localhost:8080/api/v1/employees \
  -H "Authorization: Bearer <your-token>"
```

### 2. Get Employee by ID
```bash
curl -X GET http://localhost:8080/api/v1/employees/1 \
  -H "Authorization: Bearer <your-token>"
```

### 3. Get Employee by Code
```bash
curl -X GET http://localhost:8080/api/v1/employees/code/EMP001 \
  -H "Authorization: Bearer <your-token>"
```

### 4. Create Employee
```bash
curl -X POST http://localhost:8080/api/v1/employees \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "empCode": "EMP001",
    "firstName": "John",
    "lastName": "Doe",
    "gender": "Male",
    "dob": "1990-01-15",
    "dateOfJoining": "2023-01-01",
    "employmentType": "Full-time",
    "locationId": 1,
    "businessUnitId": 1,
    "levelId": 1,
    "subLevel": "A",
    "designationId": 1,
    "jobFamilyId": 1,
    "email": "john.doe@company.com",
    "phone": "+1234567890",
    "permanentWfh": false,
    "status": "Active"
  }'
```

### 5. Update Employee
```bash
curl -X PUT http://localhost:8080/api/v1/employees/1 \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John Updated",
    "email": "john.updated@company.com",
    "status": "Active"
  }'
```

### 6. Delete Employee
```bash
curl -X DELETE http://localhost:8080/api/v1/employees/1 \
  -H "Authorization: Bearer <your-token>"
```

## Candidate Management APIs

### 1. Get All Candidates
```bash
curl -X GET http://localhost:8080/api/v1/candidates \
  -H "Authorization: Bearer <your-token>"
```

### 2. Get Candidates by Status
```bash
curl -X GET http://localhost:8080/api/v1/candidates/status/applied \
  -H "Authorization: Bearer <your-token>"
```

### 3. Get Candidates by Date Range
```bash
curl -X GET "http://localhost:8080/api/v1/candidates/date-range?startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer <your-token>"
```

### 4. Create Candidate
```bash
curl -X POST http://localhost:8080/api/v1/candidates \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "candidateName": "Jane Smith",
    "candidateEmail": "jane.smith@email.com",
    "candidatePhone": "+1234567890",
    "appliedDate": "2024-01-15",
    "appliedPosition": "Software Engineer",
    "sourceId": 1,
    "recruiterUserId": 1,
    "status": "applied",
    "experienceYears": 3.5,
    "candidateExperienceScore": 8.5
  }'
```

## Performance Review APIs

### 1. Get All Performance Reviews
```bash
curl -X GET http://localhost:8080/api/v1/performance-reviews \
  -H "Authorization: Bearer <your-token>"
```

### 2. Get Performance Reviews by Employee
```bash
curl -X GET http://localhost:8080/api/v1/performance-reviews/employee/1 \
  -H "Authorization: Bearer <your-token>"
```

### 3. Get Performance Reviews by Date Range
```bash
curl -X GET "http://localhost:8080/api/v1/performance-reviews/date-range?startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer <your-token>"
```

### 4. Create Performance Review
```bash
curl -X POST http://localhost:8080/api/v1/performance-reviews \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "reviewerId": 2,
    "reviewPeriodStart": "2024-01-01",
    "reviewPeriodEnd": "2024-06-30",
    "reviewDate": "2024-07-15",
    "score": 4.2,
    "ratingScale": "1-5",
    "comments": "Excellent performance with room for improvement in leadership skills."
  }'
```

## Performance Improvement Plan APIs

### 1. Get All PIPs
```bash
curl -X GET http://localhost:8080/api/v1/performance-improvement-plans \
  -H "Authorization: Bearer <your-token>"
```

### 2. Get PIPs by Employee
```bash
curl -X GET http://localhost:8080/api/v1/performance-improvement-plans/employee/1 \
  -H "Authorization: Bearer <your-token>"
```

### 3. Get PIPs by Status
```bash
curl -X GET http://localhost:8080/api/v1/performance-improvement-plans/status/open \
  -H "Authorization: Bearer <your-token>"
```

### 4. Create PIP
```bash
curl -X POST http://localhost:8080/api/v1/performance-improvement-plans \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "startDate": "2024-01-01",
    "endDate": "2024-03-31",
    "objectives": "Improve communication skills and project delivery timelines",
    "actions": "1. Attend communication workshop 2. Weekly check-ins with manager 3. Complete projects on time",
    "status": "open",
    "notes": "Employee shows willingness to improve"
  }'
```

## Recruitment Metrics APIs

### 1. Get All Recruitment Metrics
```bash
curl -X GET http://localhost:8080/api/v1/recruitment-metrics \
  -H "Authorization: Bearer <your-token>"
```

### 2. Get Metrics by Channel
```bash
curl -X GET http://localhost:8080/api/v1/recruitment-metrics/channel/1 \
  -H "Authorization: Bearer <your-token>"
```

### 3. Create Recruitment Metrics
```bash
curl -X POST http://localhost:8080/api/v1/recruitment-metrics \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "recruitmentChannelId": 1,
    "metricDate": "2024-01-31",
    "costSpent": 5000.00,
    "hiresCount": 3,
    "avgTimeToHireDays": 25.5,
    "metadata": "{\"campaign\": \"Q1-2024\", \"region\": \"North\"}"
  }'
```

## Dashboard APIs

### 1. Get Dashboard Statistics
```bash
curl -X GET http://localhost:8080/api/v1/dashboard/stats \
  -H "Authorization: Bearer <your-token>"
```

## Master Data APIs

### 1. Get All Master Data
```bash
curl -X GET http://localhost:8080/api/v1/master-data \
  -H "Authorization: Bearer <your-token>"
```

### 2. Get Locations Only
```bash
curl -X GET http://localhost:8080/api/v1/master-data/locations \
  -H "Authorization: Bearer <your-token>"
```

### 3. Get Business Units Only
```bash
curl -X GET http://localhost:8080/api/v1/master-data/business-units \
  -H "Authorization: Bearer <your-token>"
```

## User Management APIs

### 1. Create User (Admin only)
```bash
curl -X POST http://localhost:8080/api/v1/admin/users \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@company.com",
    "password": "password123",
    "fullName": "New User",
    "role": "EMPLOYEE",
    "employeeId": "1"
  }'
```

## Health Check

### 1. Health Check (No authentication required)
```bash
curl -X GET http://localhost:8080/api/v1/health
```

## Response Examples

### Employee Response
```json
{
  "id": 1,
  "empCode": "EMP001",
  "firstName": "John",
  "lastName": "Doe",
  "gender": "Male",
  "dob": "1990-01-15",
  "dateOfJoining": "2023-01-01",
  "employmentType": "Full-time",
  "locationCity": "New York",
  "businessUnitName": "Engineering",
  "levelName": "Senior",
  "subLevel": "A",
  "designationTitle": "Software Engineer",
  "jobFamilyName": "Technology",
  "managerName": "Jane Manager",
  "email": "john.doe@company.com",
  "phone": "+1234567890",
  "permanentWfh": false,
  "status": "Active",
  "createdAt": "2024-01-01T10:00:00Z",
  "updatedAt": "2024-01-01T10:00:00Z"
}
```

### Dashboard Stats Response
```json
{
  "totalEmployees": 150,
  "totalCandidates": 45,
  "activeCandidates": 23,
  "hiredCandidates": 12,
  "totalPerformanceReviews": 89,
  "activePIPs": 5
}
```

## Error Response Format
```json
{
  "timestamp": "2024-01-01T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Employee not found with id: 999"
}
```

## Notes
- All endpoints require authentication except `/api/v1/health`
- Role-based access control is implemented for different operations
- Date format: `YYYY-MM-DD` (ISO 8601)
- DateTime format: `YYYY-MM-DDTHH:mm:ssZ` (ISO 8601 with timezone)
- All monetary values are in decimal format
- Boolean values: `true` or `false`