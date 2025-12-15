# 📞 PhoneBook Java Swing Application

A desktop-based **Phone Book Management System** built using **Java Swing** and **MySQL**.  
This application allows users to manage contacts efficiently with features such as adding, searching, editing, deleting contacts, and displaying profile photos.

---

## ✨ Features

- User authentication (Login system)
- Add new contacts with profile photo
- Search contacts by name
- Edit existing contact details
- Delete contacts
- Display contacts in a JTable
- Circular profile image rendering
- Default avatar for contacts without photos
- MySQL database connectivity using JDBC

---

## 🛠️ Technologies Used

- Java (Swing, AWT)
- MySQL
- JDBC
- Apache NetBeans IDE
- Git & GitHub

---

## 📁 Project Structure

PhoneBookApp/
├── src/
│ └── PhoneBook/
│ ├── ConnectionClass.java
│ ├── Login.java
│ ├── Home.java
│ ├── EntryData.java
│ ├── SearchData.java
│ ├── SearchDatatable.java
│ ├── SearchDatatableForEdit.java
│ ├── SearchNameForEdit.java
│ ├── EditData.java
│ ├── DeleteContact.java
│ ├── ImageUtils.java
│ └── ImageCellRenderer.java
├── nbproject/
├── build.xml
├── manifest.mf
└── .gitignore

pgsql
Copy code

---

## 🗄️ Database Configuration

**Database Name:** `phonebookmanagement`  
**Table Name:** `add_contact`


CREATE DATABASE phonebookmanagement;
USE phonebookmanagement;

CREATE TABLE add_contact (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    phone VARCHAR(15),
    email VARCHAR(100),
    address VARCHAR(255),
    profile_pic VARCHAR(255)
);

---

▶️ How to Run the Project

Open Apache NetBeans

Import the project (PhoneBookApp)

Configure MySQL credentials in ConnectionClass.java

Start MySQL server

Create database and table using the SQL above

Run Login.java

---

🔐 MySQL Connection Example

java
Copy code
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/phonebookmanagement",
    "root",
    "your_password"
);

---

👤 Author
Krishna Vamsi
GitHub: https://github.com/KrishnaVamsi28
