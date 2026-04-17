# Task management system

## The essence of the project:

This project allows to create and store tasks for users with division by groups.
The project includes administrator and user roles.
Administrators can create, edit, and delete tasks, distribute users by groups and assign users on tasks according to group.
Users can read their tasks and change status of task.

## Launching the application

You can deploy and launch the application using IntelliJ IDEA Ultimate or Docker, Docker Compose.

To launch via IntelliJ IDEA Ultimate, you need to add the following environment variables and assign them the appropriate values:
1) DB_NAME - database name
2) DB_USERNAME - username for connecting to the database
3) DB_PASSWORD - password for connecting to the database
4) JWT_SECRET - secret for creating java web token
   After adding the environment variables you can launch the application.

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
http://localhost:8080/auth/login - user authorization

Requests available to an authorized user:
GET-request:
http://localhost:8080/tasks/my_tasks - display a list of tasks for current user
http://localhost:8080/tasks/my_tasks/{id} - display task with id={id} from tasks for current user
PATCH-request:
http://localhost:8080/task/{id}/status - change status of task with id={id} from tasks for current user


Requests available to authorized users with administrator rights
GET-requests:
http://localhost:8080/groups - display a list of groups
http://localhost:8080/groups - display group with id={id}
http://localhost:8080/tasks - display a list of tasks
http://localhost:8080/tasks/{id} - display task with id={id}
http://localhost:8080/users - display a list of users
http://localhost:8080/users/{id} - display users with id={id}
POST-requests:
http://localhost:8080/groups - add a new group to the database
http://localhost:8080/tasks - add new task to the database
PATCH-request:
http://localhost:8080/groups/{id} - edit name of group with id={id}
http://localhost:8080/groups/{id}/add_user - add user to list of users relating to the group with id={id}
http://localhost:8080/tasks/{id} - edit task with id={id}
http://localhost:8080/users/{id} - change role of user with id={id}
DELETE-request:
http://localhost:8080/groups/{id} - delete group with id={id}
http://localhost:8080/tasks/{id} - delete task with id={id}
http://localhost:8080/users/{id} - delete user with id={id}

More detailed information can be found in the swagger documentation at this link (the page is available while project is run):
http://localhost:8080/swagger-ui/index.html

You can send instructions using the postman