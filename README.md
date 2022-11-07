# MembershipAPI
## Synoptic Project Apprenticeship
### Overview
Minimum Vial Product - MVP of a RESTful API to allow employees of Bows Formula One to:
- Register
- Top up
- Purchase
- LogOut
- Timeout

To see the ticket structure (BDD) for the functional requirements here [functional]

### Development tools
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
Two h2 in-memory databases were created

- One as part of the dev mode. It has the file 'resources/import.sql' that inserts data during build time, so the db is not empty [import]
- One for testing. It has the file 'resources/META_INF/import-test.sql' that inserts data during build time, so the db is not empty [import-test]

See h2 Console view here [h2 console]

### Patterns used
- Repository pattern

### Testing:
quarkus-junit5 2.13.0.final
rest-assured 5.2.0

To see tests template see [test]

# API Documentation:

### API structure
Swagger from quarkus-smallrye-openapi 2.13.0.final

See Swagger overall API structure screenshots [swagger]

### Database Model
Can be found:
- Online [DB model]
- Here in project [DB model in folder]

### System Workflow
Can be found:
- Online [System Workflow]
- Here in project [System Workflow in folder]

More information can be found at [trello agile board] and this folder [agile board] contains

- Screenshot of trello board in .png
- all tickets of trello board in .pdf (It doesn't contain the attachments to the tickets, only the text)

See localhost:8081 page

[trello agile board]: https://trello.com/invite/b/sKd4SgK2/ATTI5889f061bd9f84acf881467c7ed078ba71C1849A/synoptic-project-membership-api
[DB model]: https://drive.google.com/file/d/158d_RRCAMzvkQAggmk-H2czs_RS2iPC3/view?usp=sharing
[DB model in folder]: documentation/model_DB/DB_Catering_System.drawio.png
[System Workflow]: https://drive.google.com/file/d/1w75-TmkBC9HULxGfh6feBDvGzmauZWzV/view?usp=sharing
[System Workflow in folder]: documentation/workflow_API/Workflow_API.drawio.png
[swagger]: documentation/swagger/Swagger%20Overall%20Structure.pdf
[import]: src/main/resources/import.sql
[import-test]: src/main/resources/META-INF/import-test.sql
[h2 console]: documentation/h2_Console_View/H2%20in-memory%20DB.pdf
[functional]: documentation/requirements/Functional%20Requirements.pdf
[test]: documentation/test/Tests_API.pdf
[agile board]: documentation/agile_Board