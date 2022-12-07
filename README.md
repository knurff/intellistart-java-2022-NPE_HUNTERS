# Interview Planning Application
## NPE-Hunters team

### Overview
* [About the project](#about-the-project)
* [API](#api)
* [Setting up the project](#setting-up-the-project)

### About the project

The interview planning application is a RESTful service that provides communication and interview scheduling capabilities for interviewers and candidates via coordinators.

The application supports the following functionality:
- Create slots for Interviewer
- Create slots for Candidate
- Create bookings for Interviewer and Candidate slots as Coordinator

Clarification on terms used:
- Guest is a non-authenticated person
- User is a person who completed the authentication process
- Candidate is a user without any auth role
- Interviewer is a user with an auth role of INTERVIEWER
- Coordinator is a user with an auth role of COORDINATOR

Technologies used:
* Java 11
* PostgreSQL
* Spring Boot
* Spring Data
* Spring Security
* OAuth 2.0 with Facebook API

### API
### User
- `GET /me` - return user info
- `GET /weeks/next` - get next week number
- `GET /weeks/current` - get current week number
- `POST /auth/login` - authentication

#### Guest
- `POST /auth/login` - authentication

#### Interviewer
- `POST /interviewers/<interviewerId>/slots` - create interviewer slot
- `POST /interviewers/<interviewerId>/slots/{slotId}` - update interviewer slot
- `GET /interviewers/<interviewerId>/slots` - get all interviewer slots
- `POST /interviewers/<interviewerId>/bookings` - set the maximum booking quantity for the next week

#### Candidate
- `POST /candidates/current/slots` - create a candidate slot
- `POST /candidates/current/slots/{slotId}` - update a candidate slot
- `GET /candidates/current/slots` - get all candidate slots

#### Coordinator
- `GET /weeks/{weekId}/dashboard` - get all interviewer and candidate slots
- `POST /interviewers/{interviewerId}/slots/{slotId}` - update an interviewer slot
- `POST /bookings` - create a booking
- `POST /bookings/{bookingId}` - update a booking
- `DELETE /bookings/{bookingId}` - delete a booking
- `POST /users/interviewers` - grant Interviewer role to a user
- `DELETE /users/interviewers/<interviewer-id>` - revoke Interviewer role from a user
- `GET /users/interviewers` - get list of all interviewers 
- `POST /users/coordinators` - grant Coordinator role to a user
- `DELETE /users/coordinators/<coordinator-id>` - revoke coordinator role
- `GET /users/coordinators` - get list of all coordinators

### Setting up the project

Setting up a project with docker:
In order to create a project image, run:
- `docker build -t image_name:tag .` (dot character specifies current directory as path for Dockerfile)

The project utilizes two dockerfiles:
- `Dockerfile` - main docker file for provisioning an API image 
- `Dockerfile.db` - docker file to create an image of database based on PostgreSQL prefilled with data from src/main/resources/init.sql 

There are two ways of running this application locally:  
If you want to run our release version  
- `docker-compose -f docker-compose.api-pull.yml up -d` to start  
- `docker-compose -f docker-compose.api-pull.yml down` to stop  
If you are making changes to the application  
- `docker-compose -f docker-compose.api-build.yml up -d` to start  
- `docker-compose -f docker-compose.api-build.yml up -d` to stop  

The project utilizes three docker compose files:
- `docker-compose.db-only` - docker compose to provision only a database based on Dockerfile.db docker file
- `docker-compose.api-build` - same as docker-compose.db-only, but also build the api and create connection between it and a prefilled database
- `docker-compose.api-pull` - same as docker-compose.api-build albeit we pull an API image from this project's Dockerhub repo instead of building it locally
