Rest API for postupicheck project
==================================

### Requirements

- jdk 18 (You can use IDEA to install and configure)
- Postgress 14 [Download PostgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
- Maven 3.8.5 (or mvnw)

### Setup

#### Environment variables

- JAVA_HOME

### Create DB 

1. Run script `create_db.sql` in pgAdmin to create db (default name is _JoinMoreDB_)
2. Modify db connection in `src/main/resources/application.properties` if needed

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/{dbname}
spring.datasource.username={login}
spring.datasource.password={pass}
```
### Build project

```bash
  .\mvnw clean install
```

### Run project

```bash
  .\mvnw spring-boot:run
```

### Import test data

Currently `test-data` endpoints are used.

Import order:

```bash

http POST http://localhost:8080/test-data/create-subjects

http POST http://localhost:8080/test-data/create-students

http POST http://localhost:8080/test-data/create-universities

	http POST http://localhost:8080/test-data/create-courses
	
	http POST http://localhost:8080/test-data/create-examresults
	
	http POST http://localhost:8080/test-data/create-admissions
	
```

Import can take several hours (examresults, admissions) depending on hardware

### Alternative way

- Download database dump
- Restore db from dump

TODO instruction how to do this
