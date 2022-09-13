Rest API for postupicheck project
==================================

### Requirements

- jdk 18 (You can use IDEA to install and configure all project)
- Postgress 14 [Download PostgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
- Maven 3.8.5 (or mvnw)

### Setup

#### Environment variables

- JAVA_HOME
- MAVEN_HOME

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

### Import test data (DEPRECATED)

Currently `test-data` and `data` endpoints are used.

Import order:

```bash

http POST http://localhost:8080/test-data/create-subjects

http POST http://localhost:8080/test-data/create-students

http POST http://localhost:8080/test-data/create-universities

	http POST http://localhost:8080/test-data/create-courses
	
	http POST http://localhost:8080/test-data/create-examresults
	
	http POST http://localhost:8080/test-data/create-admissions
	
	http POST http://localhost:8080/test-data/set-points-for-all-admissions
	
	http POST http://localhost:8080/data/update-passing-scores
	
```

Import can take several minutes (admissions) depending on hardware
### Alternative way

1. [Download JoinMoreDB backup](https://disk.yandex.ru/d/XS7QdutzKNGcgQ)
2. [Restore dump file to PostgreSQL](https://www.postgresql.org/docs/current/app-pgrestore.html)

