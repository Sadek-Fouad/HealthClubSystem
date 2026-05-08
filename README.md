# Health Club Management System

A Java-based Health Club Management System developed using Object-Oriented Programming (OOP) principles.  
The system helps manage administrators, coaches, members, subscriptions, billing, and reports through both a console-based system and a graphical user interface (GUI), with file handling support for data persistence.

---

## Features

### Admin Features
- Add, update, delete, and search members
- Add, delete, and list coaches
- Add, delete, and list admins
- Assign members to coaches
- Generate member reports
- Manage billing records
- Check expiring subscriptions
- Prevent deletion of the currently logged-in admin
- Prevent deletion of the last remaining admin

### Coach Features
- View assigned members
- Create workout schedules
- Set workout plans for members
- Broadcast messages to members
- Update personal information

### Member Features
- View assigned coach
- View workout schedule and training plan
- View subscription expiration date
- Receive subscription expiry warnings
- Update personal information

### GUI Features
- Login screen with role-based access
- Admin dashboard
- Coach dashboard
- Member dashboard
- Interactive buttons and forms
- Graphical interface built using Java Swing

---

## Technologies Used

- Java
- Java Swing (GUI)
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
│   └── main/
│       │
│       ├── Main.java
│       │
│       ├── GUI/
│       │   ├── GuiMain.java
│       │   ├── LoginFrame.java
│       │   ├── AdminDashboard.java
│       │   ├── CoachDashboard.java
│       │   └── MemberDashboard.java
│       │
│       ├── models/
│       │   ├── User.java
│       │   ├── Admin.java
│       │   ├── Coach.java
│       │   └── Member.java
│       │
│       └── managers/
│           ├── FileManager.java
│           ├── SystemManager.java
│           └── ReportGenerator.java
│
├── data/
│   ├── admins.txt
│   ├── coaches.txt
│   └── members.txt
│
├── .gitignore
└── README.md
```

---

## Data Persistence

The system stores core data using text files, allowing information such as members, coaches, and admins to persist between program executions.

---

## How to Run

### Console Version
1. Open the project in IntelliJ IDEA
2. Run the `Main.java` file
3. Use the console menu to interact with the system

### GUI Version
1. Open the project in IntelliJ IDEA
2. Run the `GuiMain.java` file
3. Use the graphical interface to interact with the system

---

## Sample Functionalities

- Member subscription tracking
- Days remaining calculation
- Coach assignment system
- Billing and reporting system
- Login/logout system
- File-based data storage
- GUI dashboards for different user roles

---

## Author

Sadek Fouad  
Computer Science & AI Student – Data Science Specialization
