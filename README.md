# ReimbursementProject

## Overview
- An application for Emplyoees to submit reimbursement requests for approval by a manager. 

## Tools and Technologies
- ReactJS
- EmailJS
- Notifications Manager
- CSS
- Java
- Javalin
- JUnit5
- MongoDB
- JDBC
- Log4j API

## Features
- Employees can login/logout
- Employees can submit requests
- Employees can update/view their account info (username, password, email)
- Employees can recover their acccount, if they forget their password using their email
- Employees are sent an email upon approval or denial of their reimbursement requests
- Employees can view their "pending", "approved" and "denied" requests
- Managers can login/logout
- Managers can approve/deny any requests
- Managers can view users "pending", "approved" and "denied" requests
- Managers can view all Employees 
- Managers can recover their acccount, if they forget their password using their email
- Managers can update/view their account info (username, password, email)

## Getting Started

To get this application setup:
1. Must have nodejs install.
2. Must have Java 8 runtime environment installed.

If the requirements are met, go ahead and clone the repo by using the command below:
```git clone https://gitlab.com/revature_batches/0412_javapp_august/-/tree/David_Jasuan/Project%201```

Once the repository is cloned you will need to make sure the localhost mongodb server is running.
The functionalities of the frontend will not work if http://localhost:3000/ is not running. The javalin server is running at port 8080.

## To run frontend program with npm
```npm install``` 
```npm start``` 

## To run the backend
Run the JavalinRouter main application

## Contributors
- David Jasuan
