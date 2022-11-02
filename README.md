# MembershipAPI
## Synoptic Project Apprenticeship

- Java 17 or later
- Maven 3.8.1 or later
- Quarkus 2.13.0.final
- Make sure GIT_HOME, MVN_HOME and JAVA_HOME are set and they are in your PATH variable

### For connection to h2 console go to
```bash
http://localhost:8081/h2
```
Once in the h2 console type the following JDBC URL:
```bash
jdbc:h2:mem:membershipDB
```

### Patterns used
- Repository pattern
### Testing:
Jacoco
quarkus-junit5 2.13.0.final
rest-assured 5.2.0

### Documentation:
Swagger from quarkus-smallrye-openapi 2.13.0.final

### Overview
Minimum Vial Product - MVP of a RESTful API to allow employees of Bows Formula One to:
- Register
- Top up
- Purchase

More information can be found at [trello agile board]

See localhost:8081 page

[trello agile board]: https://trello.com/b/sKd4SgK2/synoptic-project-membership-api
