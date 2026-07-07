# EVENT API specification

>The goal of this project is to provide basic features for an event-api and to practice.

---

#### Event class:
- eventId - Long
- name - String
- date of event- LocalDateTime
- duration(minutes) - int 
- date of creation - Instant
- description - string
- status - Enum: PLANNED, IN_PROGRESS, CANCELED, COMPLETED
- location - String

---

#### Feature:
should have:
- CRUD on Event
- sorting by word in description, name, date of event, location, status
- Filtering event on name,date of event

#### Cross-cutting concerns
- Logging
- Exception Handler
- api documentation

---

### API definition

base URL : events

| Short Description                       | HTTP methods | URL                          | Description                                           | URL parameters | Request Body     |
| --------------------------------------- | ------------ | ---------------------------- | ----------------------------------------------------- | -------------- | ---------------- |
| Create an event                         | POST         | /events                       | create an event                                        | -              | new event        |
| Read all events                         | GET          | /events                       | list event(MAX 25)                                    | -              | -                |
| Read a specific event                   | GET          | /events/{eventId}             | get one event by id                                   | eventId             | -                |
| Update all data of a specific event     | PUT          | /events/{eventId}             | update all information in an event **except** event id | eventId             | complete event    |
| Update part of data of a specific event | PATCH        | /events/{eventId}             | update a part of an event                             | eventId       | fields to update |
| Delete an event                         | DELETE       | /events/{eventId}             | delete an event                                        | eventId       | -                |
| Sort event by event properties          | GET          | /events?sort=name,dateOfEvent | get an event that match with a list of criteria         | properties     | -                |

---


### Database schema



### Technical description

I don't want to use plugins like:
- Spring Data REST
- Spring Data JPA
- JacksonMapper to map object with json file
We will use hibernate but we are going to write the SQL query ourselves

I chose to use :
- mysql as DB for dev
- H2 for testing
- spring boot for backend
- Flyway for migration
---

### Planification (sketch)
1. create issue for the following steps
2. generate initial spring project
3. create the CI pipeline
4. set up Docker and Mysql
5. create Event entity
6. CRUD Event
7. Unit test
8. logger for event
9. exception handler
