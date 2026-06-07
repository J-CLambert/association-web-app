# EVENT API spesification

>This project is have as goal to provide basic feature for an association web app and to practice.

---

#### Event class:
- eventId - int
- name - string
- date of event- date
- duration(minutes) - int 
- date of creation - date
- description - string

---

#### Feature:
should have:
- CRUD on Event
- A logger
- manginge exection
- sorting event on name,date of event
- api documentation
- log managment
- global exeception handler
must to have:
- sorting by world in description
- docker

---

### API definition

base URL : events

| Short Description                       | HTTP methods | URL                          | Description                                           | URL parameters | Request Body     |
| --------------------------------------- | ------------ | ---------------------------- | ----------------------------------------------------- | -------------- | ---------------- |
| Create an event                         | POST         | events                       | create a event                                        | -              | new event        |
| Read all events                         | GET          | events                       | list all event                                        | -              | -                |
| Read a specific event                   | GET          | events                       | get one event by id                                   | id             | -                |
| Update all data of a specific event     | PUT          | events                       | update all information in a event **except** event id | -              | complet event    |
| Update part of data of a specific event | PATCH        | events/{eventId}             | update a part of an event                             | eventI d       | fields to update |
| Delete an event                         | DELETE       | events/{eventId}             | delete a event                                        | event id       | -                |
| Sort event by event properties          | get          | events?sort=name,dateOfEvent | get a event that match withe list of criteria         | properties     | -                |

---


### Database schema



### Technical description

We want to use low "level of control".This means that we will not use plugin as:
- Spring Data REST
- Spring DATA JPA
- JacksonMapper to map object with json file
We will use hibernate but we going to rite the SQL query our-self

I chose to use :
- mysql as DB
- spring boot for backend

---
