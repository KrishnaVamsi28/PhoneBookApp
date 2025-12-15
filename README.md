# 📞 PhoneBook Java Swing Application

A desktop-based Phone Book Management System developed using **Java Swing** and **MySQL**.  
This application allows users to manage contacts efficiently with profile photos.

---

## 🚀 Features

- User Login Authentication
- Add new contacts with profile picture
- Search contacts by name
- Edit contact details
- Delete contacts
- Display contacts in table format
- Circular profile photo rendering
- MySQL database integration

---

## 🛠️ Technologies Used

- **Java (Swing, AWT)**
- **MySQL**
- **JDBC**
- **Apache NetBeans IDE**
- **Git & GitHub**

---

## 🗂️ Project Structure

PhoneBookApp/
│
├── src/PhoneBook/
│ ├── Login.java
│ ├── Home.java
│ ├── EntryData.java
│ ├── SearchData.java
│ ├── SearchDatatable.java
│ ├── EditData.java
│ ├── DeleteContact.java
│ ├── ImageUtils.java
│ └── ImageCellRenderer.java
│
├── nbproject/
├── build.xml
├── manifest.mf
└── .gitignore

pgsql
Copy code

---

## 🗄️ Database Details

**Database Name:** `phonebookmanagement`  
**Table Name:** `add_contact`

### Table Structure
```sql
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
Open NetBeans

Open Project → PhoneBookApp

Configure MySQL credentials in ConnectionClass.java

Create database and table using SQL above

Run Login.java

---

📸 Screenshots
(Add screenshots here if required)

---

👤 Author
Krishna Vamsi
GitHub: https://github.com/KrishnaVamsi28
