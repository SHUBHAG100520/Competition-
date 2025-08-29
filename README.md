# Webhook SQL Runner - Spring Boot Application

## Overview

This is a Spring Boot application designed to automatically solve a SQL query challenge based on a webhook API interaction. The application:

- Sends a POST request immediately on startup to generate a webhook URL and receive an access JWT token.
- Based on an assigned SQL problem (depending on registration number), constructs the SQL query solution.
- Submits the final SQL query solution to the webhook URL with proper JWT authorization.
- Runs the entire flow automatically at application startup without any manual endpoints or inputs.

---

## Project Structure

webhook-sql-runner/
│
├── src/
│ ├── main/
│ │ ├── java/com/example/webhook_sql_runner/
│ │ │ ├── WebhookRequest.java # POJO for initial POST request body
│ │ │ ├── WebhookResponse.java # POJO for webhook generation API response
│ │ │ ├── StartupRunner.java # CommandLineRunner to kick start webhook calls and submission
│ │ │ └── WebhookSqlRunnerApplication.java # Main Spring Boot entry point
│ │ └── resources/
│ │ └── application.properties # (Optional Spring config)
│ ├── test/ ...
│
├── pom.xml # Maven build definition
├── README.md # Project README
└── target/ # Compiled JAR output (after build)

text

---

## How It Works

1. **Startup Trigger**

   - The app implements `CommandLineRunner` via `StartupRunner` class.
   - As soon as the Spring Boot app boots, the `run()` method triggers the webhook generation process.

2. **Webhook Generation Request**

   - Sends a POST request to the API endpoint covering user details (`name`, `regNo`, `email`).
   - The API responds with:
     - `webhook` URL for submitting final answers.
     - An `accessToken` JWT for authorization.

3. **Solve SQL Challenge**

   - The app analyzes the registration number to pick the correct SQL problem.
   - It constructs the SQL query string solution in Java.

4. **Submit SQL Solution**

   - Posts the SQL query string to the returned `webhook` URL.
   - Includes the `Authorization: Bearer <accessToken>` header using JWT.
   - Displays API submission response on console.

---

## Setup & Run Instructions

### Prerequisites

- Java 17 or later installed
- Maven installed (or use included `mvnw` wrapper)
- Internet connectivity for API calls

### Build

./mvnw clean package

text

### Run

java -jar target/webhook-sql-runner-0.0.1-SNAPSHOT.jar

text

---

## Key Code Snippet

String finalQuery = "SELECT "
+ "p.amount AS SALARY, "
+ "CONCAT(e.first_name, ' ', e.last_name) AS NAME, "
+ "TIMESTAMPDIFF(YEAR, e.dob, p.payment_time) AS AGE, "
+ "d.department_name AS DEPARTMENT_NAME "
+ "FROM payments p "
+ "JOIN employee e ON p.emp_id = e.emp_id "
+ "JOIN department d ON e.department = d.department_id "
+ "WHERE DAY(p.payment_time) != 1 "
+ "ORDER BY p.amount DESC "
+ "LIMIT 1;";

text

---

## Charts and Visualization (Example)

You may include charts to visualize:

### API Request Flow

| Step                     | Description                     | Status        |
|--------------------------|---------------------------------|---------------|
| POST to generate webhook  | Sends user details and receives webhook and token | Successful |
| POST to submit SQL query  | Sends final SQL query with JWT  | Successful |

---

### SQL Query Performance (Hypothetical)

| Metric           | Value         |
|------------------|---------------|
| Rows scanned     | 50,000        |
| Execution time   | 120 ms        |
| Result rows      | 1             |

(A real chart of this could be generated using performance tools or logs.)

---

## GitHub Repo and Submission

- The full source code is hosted at:  
  `(https://github.com/SHUBHAG100520/Competition-/tree/main/webhook-sql-runner)`

- Downloadable JAR:  
  `https://github.com/your-username/webhook-sql-runner/releases/download/v1.0/webhook-sql-runner-0.0.1-SNAPSHOT.jar`

---

## Additional Notes

- The project uses **Spring Boot 3.5.5** with **RestTemplate** for REST calls.
- JWT authentication token handling is built-in via Authorization header.
- The app flow is fully automated on startup—no manual triggers needed.
- You can extend the project with logging, error handling, and unit tests as next steps.

---

## Contact

For any questions or queries, contact:

`Subham Agarwal`  
`shubhag0411@gmail.com`

---

