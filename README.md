# Employee Management System Setup Guide

This guide provides step-by-step instructions to set up and run the Employee Management System.

## Prerequisites
- Git
- Java Development Kit (JDK)
- MySQL Server

## Setup Instructions

1. **Clone the Project**: 
    ```
    git clone <https://github.com/BSSowmya/Case-Study-2.git>
    ```

2. **Build Each Module**: 
    ```
    cd <Case-Study-2>
    mvn clean install
    mvn clean package


    ```

3. **Set Up PostgreSQL Databases**: 
    - Create databases named "case_study" in your Postgres server.

4. **Start the DiscoveryHub**: 
    - Launch the DiscoveryHub service.

5. **Start the Employee Service and User Service Application**: 
    - After building, start both the employee service and the User Service application.


6. **Explore Swagger UI**: 
    - Access API documentation at the following URLs:
        - User service: [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#)
        - Employee service: [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#)
        - DiscoveryHub: [http://localhost:8761](http://localhost:8761)

7. **Authorization**: 
    - To access any API in the employee service, authorization is required. Log in to Swagger, click on the "Authorize" button, and enter the JWT provided in the response of the `api/auth/signin` API from the user application.

8. **Utilize Employee Service APIs**:
    - Upload employee records: Utilize the `/employees/upload` endpoint. A sample file format is provided via `/employees/sampleCSV`.
    - Modify employee records by ID: Use the `/employees` endpoint with HTTP PUT method.
    - Search for a specific employee by ID: Access `/employees/{id}` endpoint.
    - Search for employees by applying filters: Utilize the `/employees/search` endpoint.
    - Export filtered results as a CSV file: Send the filter parameters to `/employees/export`.
