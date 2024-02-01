## Task

### Business Requirements:

1. Develop web service for Gift Certificates system with the following entities (many-to-many):
![image](https://github.com/laziz511/EPAM-Spring-Task/assets/110926594/8a2ff957-c459-443d-8c17-7c6157f1c3bd)

CreateDate, LastUpdateDate - format ISO 8601 (https://en.wikipedia.org/wiki/ISO_8601). Example: 2018-08-29T06:12:15.156. More discussion here: https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute
Duration - in days (expiration period)



2. The system should expose REST APIs to perform the following operations:
- CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
- CRD operations for Tag.
- Get certificates with tags (all params are optional and can be used in conjunction):
    - by tag name (ONE tag)
    - search by part of name/description (can be implemented, using DB function call)
    - sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).

3. The system should be extended to expose the following REST APIs:
- Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
- Add new entity User implement only get operations for user entity.

- Make an order on gift certificate for a user (user should have an ability to buy a certificate).
- Get information about user’s orders.
- Get information about user’s order: cost and timestamp of a purchase.
- The order cost should not be changed if the price of the gift certificate is changed.

- Get the most widely used tag of a user with the highest cost of all orders. Create separate endpoint for this query. Demonstrate SQL execution plan for this query (explain).

- Search for gift certificates by several tags (“and” condition).
- Pagination should be implemented for all GET endpoints. Please, create a flexible and non-erroneous solution. Handle all exceptional cases.
- Support HATEOAS on REST endpoints.

4. Spring Security should be used as a security framework.
- Application should support only stateless user authentication and verify integrity of JWT token.
- Users should be stored in a database with some basic information and a password.
- User Permissions:
![image](https://github.com/laziz511/EPAM-Spring-Task/assets/110926594/a697bdd9-f0c7-4fab-90b7-8a062684d9a9)


### Application Requirements:
- Application packages root: com.epam.esm
- Any widely-used connection pool could be used.
- JDBC / Spring JDBC Template should be used for data access.
- Use transactions where it’s necessary.
- Java Code Convention is mandatory (exception: margin size – 120 chars).
- Build tool: Maven/Gradle, latest version. Multi-module project.
- Web server: Apache Tomcat/Jetty.
- Application container: Spring IoC. Spring Framework, the latest version.
- Database: PostgreSQL/MySQL, latest version.
- Testing: JUnit 5.+, Mockito.
- Service layer should be covered with unit tests not less than 80%.
- Repository layer should be tested using integration tests with an in-memory embedded database (all operations with certificates).


### General Requirements:
- Code should be clean and should not contain any “developer-purpose” constructions.
- App should be designed and written with respect to OOD and SOLID principles.
- Code should contain valuable comments where appropriate.
- Public APIs should be documented (Javadoc).
- Clear layered structure should be used with responsibilities of each application layer defined.
- JSON should be used as a format of client-server communication messages.
- Convenient error/exception handling mechanism should be implemented: all errors should be meaningful and localized on backend side. 
- Abstraction should be used everywhere to avoid code duplication.
- Several configurations should be implemented (at least two - dev and prod).
- Application restrictions: It is forbidden to use: Spring Boot, Spring Data Repositories JPA, Powermock (your application should be testable).

### Application requirements:
- Hibernate should be used as a JPA implementation for data access.
- Spring Transaction should be used in all necessary areas of the application.
- Audit data should be populated using JPA features (an example can be found in materials).

### Application restrictions:
- Hibernate specific features.
- Spring Data


### Deploy application to AWS



