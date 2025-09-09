# HR Strategix API Testing Examples

## Authentication

First, login to get an access token:

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "identifier": "rajesh_admin",
    "password": "hashed_pw"
  }'
```

Use the returned `accessToken` in the Authorization header for subsequent requests:
```
Authorization: Bearer <your-access-token>
```

## User Management APIs

### 1. Get Current User Profile
```bash
curl -X GET http://localhost:8080/api/v1/auth/me \
  -H "Authorization: Bearer <your-token>"
```

### 2. Get All Users (Admin only)
```bash
# Get all users
curl -X GET http://localhost:8080/api/v1/admin/users \
  -H "Authorization: Bearer <your-token>"

# Filter by role
curl -X GET "http://localhost:8080/api/v1/admin/users?role=employee" \
  -H "Authorization: Bearer <your-token>"

# Filter by username
curl -X GET "http://localhost:8080/api/v1/admin/users?username=anita" \
  -H "Authorization: Bearer <your-token>"

# Pagination
curl -X GET "http://localhost:8080/api/v1/admin/users?page=0&size=5" \
  -H "Authorization: Bearer <your-token>"
```

### 3. Get User by ID
```bash
curl -X GET http://localhost:8080/api/v1/admin/users/2 \
  -H "Authorization: Bearer <your-token>"
```

### 4. Create User
```bash
curl -X POST http://localhost:8080/api/v1/admin/users \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "email": "test.user@company.com",
    "password": "password123",
    "fullName": "Test User",
    "role": "employee",
    "employeeId": "2"
  }'
```

### 5. Update User
```bash
curl -X PUT http://localhost:8080/api/v1/admin/users/2 \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Anita Sharma Updated",
    "role": "line_manager",
    "isActive": true
  }'
```

### 6. Deactivate User (Admin only)
```bash
curl -X DELETE http://localhost:8080/api/v1/admin/users/2 \
  -H "Authorization: Bearer <your-token>"
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
    "empCode": "E1006",
    "firstName": "Ravi",
    "lastName": "Kumar",
    "gender": "Male",
    "dob": "1992-03-20",
    "dateOfJoining": "2024-01-15",
    "employmentType": "permanent",
    "locationId": 1,
    "businessUnitId": 1,
    "levelId": 1,
    "subLevel": "A",
    "designationId": 1,
    "jobFamilyId": 1,
    "email": "ravi.kumar@company.com",
    "phone": "+1234567890",
    "permanentWfh": false,
    "status": "active"
  }'
```

### 5. Update Employee
```bash
curl -X PUT http://localhost:8080/api/v1/employees/1 \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Rajesh Updated",
    "email": "rajesh.updated@company.com",
    "status": "active"
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
    "candidateName": "Rohit Sharma",
    "candidateEmail": "rohit.sharma@email.com",
    "candidatePhone": "+9876543210",
    "appliedDate": "2024-07-15",
    "appliedPosition": "Senior Software Engineer",
    "sourceId": 1,
    "recruiterUserId": 4,
    "status": "applied",
    "experienceYears": 5.0,
    "candidateExperienceScore": 4.2
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
    "employeeId": 2,
    "reviewerId": 1,
    "reviewPeriodStart": "2024-01-01",
    "reviewPeriodEnd": "2024-06-30",
    "reviewDate": "2024-07-15",
    "score": 4.5,
    "ratingScale": "1-5",
    "comments": "Outstanding performance, excellent technical skills and team collaboration."
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
    "employeeId": 3,
    "startDate": "2024-01-01",
    "endDate": "2024-03-31",
    "objectives": "Improve reporting accuracy and timeliness",
    "actions": "1. Weekly review meetings 2. Use reporting templates 3. Double-check all calculations",
    "status": "open",
    "notes": "Monitor progress closely, provide additional support as needed"
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
    "metricDate": "2024-07-31",
    "costSpent": 25000.00,
    "hiresCount": 2,
    "avgTimeToHireDays": 28.0,
    "metadata": "{\"campaign\": \"Q3-2024\", \"region\": \"APAC\"}"
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
  "empCode": "E1001",
  "firstName": "Rajesh",
  "lastName": "Kumar",
  "gender": "Male",
  "dob": "1985-06-15",
  "dateOfJoining": "2015-04-01",
  "employmentType": "permanent",
  "locationCity": "Bangalore",
  "businessUnitName": "Technology",
  "levelName": "L3",
  "subLevel": "C",
  "designationTitle": "Senior Software Engineer",
  "jobFamilyName": "Engineering",
  "managerName": null,
  "email": "rajesh.kumar@company.com",
  "phone": "9876543210",
  "permanentWfh": false,
  "status": "active",
  "createdAt": "2025-09-01T00:00:00Z",
  "updatedAt": "2025-09-01T00:00:00Z"
}
```

### User Response
```json
{
  "id": 1,
  "username": "rajesh_admin",
  "email": "rajesh.kumar@company.com",
  "fullName": "Rajesh Kumar",
  "role": "ADMIN",
  "employeeId": 1,
  "employeeCode": "E1001",
  "employeeName": "Rajesh Kumar",
  "isActive": true,
  "lastLoginAt": "2025-09-01T00:00:00Z",
  "createdAt": "2025-09-01T00:00:00Z"
}
```

### Sample Login Credentials
Based on the sample data, you can use these credentials for testing:

**Admin User:**
- Username: `rajesh_admin`
- Password: `hashed_pw`
- Role: ADMIN

**HR Admin:**
- Username: `meena_hr`
- Password: `hashed_pw`
- Role: HR_ADMIN

**Line Manager:**
- Username: `arjun_lead`
- Password: `hashed_pw`
- Role: LINE_MANAGER

**BU Lead:**
- Username: `amit_sales`
- Password: `hashed_pw`
- Role: BU_LEAD

**Employee:**
- Username: `anita_emp`
- Password: `hashed_pw`
- Role: EMPLOYEE

### Dashboard Stats Response
```json
{
  "totalEmployees": 12,
  "totalCandidates": 4,
  "activeCandidates": 2,
  "hiredCandidates": 2,
  "totalPerformanceReviews": 5,
  "activePIPs": 1
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
- User passwords in sample data are stored as `hashed_pw` (use NoOpPasswordEncoder for testing)
- Pagination: Use `page` (0-based) and `size` parameters for paginated endpoints
- Soft delete: Users are deactivated (isActive=false) rather than physically deleted