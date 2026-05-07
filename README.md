# Health Club Management System

A Java-based Health Club Management System developed using Object-Oriented Programming (OOP) principles.  
The system helps manage administrators, coaches, members, subscriptions, billing, and reports through a console-based application with file handling support for data persistence.

---

## Features

### Admin Features
- Add, update, delete, and search members
- Add, update, delete, and search coaches
- Assign members to coaches
- Generate member reports
- Manage billing records
- Prevent deletion of the currently logged-in admin
- Prevent deletion of the last remaining admin

### Coach Features
- View assigned members
- Create workout schedules and plans
- Broadcast messages to members

### Member Features
- View assigned coach
- View workout schedule
- View subscription expiration date
- Receive subscription expiry warnings

---

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- File Handling
- IntelliJ IDEA

---

## OOP Concepts Applied

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction

---

## Project Structure

```text
HealthClubSystem/
│
├── src/
│   ├── main/
│   ├── models/
│   └── managers/
│
├── data/
│
├── .gitignore
└── README.md
```

---

## Data Persistence

The system stores data using text files, allowing information such as members, coaches, admins, and billing records to persist between program executions.

---

## How to Run

1. Open the project in IntelliJ IDEA
2. Run the `Main.java` file
3. Use the console menu to interact with the system

---

## Sample Functionalities

- Member subscription tracking
- Days remaining calculation
- Coach assignment system
- Billing and reporting system
- Login/logout system
- File-based data storage

---

## Future Improvements

- GUI implementation
- Database integration
- Advanced authentication system
- Enhanced reporting dashboard

---

## Author

Sadek Fouad  
Computer Science & AI Student – Data Science Specialization
