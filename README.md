# EVENT API spesification

>This project is have as goal to provide basic feature for an association web app and to practice.

---

#### Event class:
- eventId - Long
- name - string
- date of event- LocalDateTime
- duration(minutes) - int 
- date of creation - Instant
- description - string
- statut - Enum: PLANNED, IN_PROGRESS, CANCELED, COMPLETED
- location - String

---

#### Feature:
should have:
- CRUD on Event
- sorting by world in description, name, date of event, location, status
- Filtering event on name,date of event

#### Cross-cutting concerns
- Logging
- Exception Handler
- api documentation
- docker

---

### API definition

base URL : events

| Short Description                       | HTTP methods | URL                          | Description                                           | URL parameters | Request Body     |
| --------------------------------------- | ------------ | ---------------------------- | ----------------------------------------------------- | -------------- | ---------------- |
| Create an event                         | POST         | events                       | create a event                                        | -              | new event        |
| Read all events                         | GET          | events                       | list all event                                        | -              | -                |
| Read a specific event                   | GET          | events/{eventId}             | get one event by id                                   | id             | -                |
| Update all data of a specific event     | PUT          | events/{eventId}             | update all information in a event **except** event id | -              | complet event    |
| Update part of data of a specific event | PATCH        | events/{eventId}             | update a part of an event                             | eventI d       | fields to update |
| Delete an event                         | DELETE       | events/{eventId}             | delete a event                                        | event id       | -                |
| Sort event by event properties          | GET          | events?sort=name,dateOfEvent | get a event that match withe list of criteria         | properties     | -                |

---


### Database schema



### Technical description

We want to use low "level of control".This means that we will not use plugin as:
- Spring Data REST
- Spring DATA JPA
- JacksonMapper to map object with json file
We will use hibernate but we going to rite the SQL query our-self

I chose to use :
- mysql as DB for dev
- H2 for testing
- spring boot for backend

---

### Planification (sketch)
1. create issue for following step
2. generate ini spring project
3. create the pipline CI
4. set up Docker and Mysql
5. create Event entity
6. CRUD Event
7. Unti test
7. logger for event
8. exception handler
