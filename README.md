# Interview Planning Application
## NPE-Hunders team

### Overview
* [About the project](#about-the-project)
* [API](#api)
* [Setting up the project](#setting-up-the-project)

### About the project

The interview planning application is a RESTful service that performs communication and interview scheduling for interviewers and candidates via coordinators.

The application supports the following functionality:
- Create slots for Interviewer.
- Create slots for  Candidate
- Create bookings for Interviewer and Candidate slots as Coordinator

### API
### All users
- `GET /me` - return user info
- `POST /auth/login` - authentication

#### Guest (no authenticated user)
- `GET /weeks/current` - get current week number
- `GET /weeks/next` - get next week number
- `POST /auth/login` - authentification

#### Interviewer (user with auth role: INTERVIEWER)
- `POST /interviewers/<interviewerId>/slots` - create interviewer slot
- `POST /interviewers/<interviewerId>/slots/{slotId}` - update interviewer slot
- `GET /interviewers/<interviewerId>/slots` - get all interviewer slot
- `POST /interviewers/<interviewerId>/bookings` - set maximum booking quantity to next week

#### Candidate (user without auth role)
- `POST /candidates/current/slots` - create candidate slot
- `POST /candidates/current/slots/{slotId}` - update candidate slot
- `GET /candidates/current/slots` - get all candidate slots

#### Coordinator (user with auth role: COORDINATOR)
- `GET /weeks/{weekId}/dashboard` - get all interviewer and candidate slots
- `POST /interviewers/{interviewerId}/slots/{slotId}` - update interviewer slot
- `POST /bookings` - create booking
- `POST /bookings/{bookingId}` - update booking
- `DELETE /bookings/{bookingId}` - delete booking
- `POST /users/interviewers` - grand Interviewer role
- `GET /users/interviewers` - get list of interviewers 
- `DELETE /users/interviewers/<interviewer-id>` - revoke interviewer role
- `POST /users/coordinators` - grand Coordinator role
- `GET /users/coordinators` - get list of coordinators
- `DELETE /users/coordinators/<coordinator-id>` - revoke coordinator role

### Setting up the project

Setup a project in docker:
In order to create a project image run:
`docker build -t "NAME:Dockerfile"`

The project perform two dockerfiles:
- `Dockerfile` - basic file to create an image 
- `Dockerfile.db` - file to create an image of database based on PostgreSQL and filled with start sql datta 

In order to run an application with docker compose, run the following:
`docker-compose -f docker-compose.custom.yml up`

The project perform three docker compose files:
- `docker-compose.db-only` - docker compose for postgres db with test data
- `docker-compose.api-build` - same as db-only, but also build api and create connection between api and db
- `docker-compose.api-pull` - same as api-build, but exept building api image, pulls image from dockerhub repo
