# Task management system

## Project overview:

This project allows users to create and store tasks, organized by groups. The system has two roles: administrator and user.

Administrators can create, edit, and delete tasks, assign users to groups, and assign users to tasks based on their group. Users can view their tasks and change the task status.

## Running the Application

You can deploy and run the application using IntelliJ IDEA Ultimate or Docker, Docker Compose.

To run via IntelliJ IDEA Ultimate, you need to add the following environment variables and assign them appropriate values:
1) DB_NAME - database name
2) DB_USERNAME - username for connecting to the database
3) DB_PASSWORD - password for connecting to the database
4) JWT_SECRET - secret for creating java web token
   Once the environment variables are set, you can launch the application.

For launching via Docker Compose you need to rename the .evn.example file to .evn or create a .evn file  in the same folder as .evn.example. In the .evn file, you need to initialize the variables from the .evn.example file with the appropriate values. Next, you need to build the image using Maven->Lifecycle->package, upload it, and run the project using the commands below.


## Upload image:

```shell
docker build -t task-management-system:0.0.1 .
```

## Deploy with Docker Compose:

```shell
docker compose up -d
```

## Stop the project:

```shell
docker compose down
```

## Endpoints:

Requests that do not require authorization:

POST-requests:
http://localhost:8080/auth/registration - user registration
http://localhost:8080/auth/login - user login

Requests available to an authorized user:
GET-request:
http://localhost:8080/tasks/my_tasks - list of tasks for the current user
http://localhost:8080/tasks/my_tasks/{id} - task with id={id} for the current user
PATCH-request:
http://localhost:8080/task/{id}/status - change the status of the task with id={id} for the current user


Requests available to authorized users with administrator rights
GET-requests:
http://localhost:8080/groups - list of groups
http://localhost:8080/groups - group with id={id}
http://localhost:8080/tasks - list of tasks
http://localhost:8080/tasks/{id} - task with id={id}
http://localhost:8080/users - list of users
http://localhost:8080/users/{id} - users with id={id}
POST-requests:
http://localhost:8080/groups - add a new group to the database
http://localhost:8080/tasks - add new task to the database
PATCH-request:
http://localhost:8080/groups/{id} - edit the name of the group with id={id}
http://localhost:8080/groups/{id}/add_user - add a user to the group with id={id}
http://localhost:8080/tasks/{id} - edit the task with id={id}
http://localhost:8080/users/{id} - change the role of the user with id={id}
DELETE-request:
http://localhost:8080/groups/{id} - delete the group with id={id}
http://localhost:8080/tasks/{id} - delete the task with id={id}
http://localhost:8080/users/{id} - delete the user with id={id}

More detailed information can be found in the swagger documentation at this link (the page is available while project is run):
http://localhost:8080/swagger-ui/index.html

You can send instructions using the postman