# 🌾 Farm To Restaurant Management System

A modern desktop management system developed using **JavaFX** and **SQL Server** to manage the complete workflow between farms, harvest batches, restaurants, drivers, and delivery operations.

The project provides an interactive graphical user interface that allows users to manage agricultural supply chain operations efficiently through a clean and user-friendly experience.

---

# 🚀 Features

## 🌱 Farm Management

* Add new farms
* Update farm information
* Delete farms
* Search farms by city
* View farm harvest batches

## 🚚 Driver Management

* Add and manage drivers
* Update driver information
* Delete drivers
* Search drivers by name
* View delivery trips

## 🌾 Harvest Batch Management

* Add harvest batches
* Update available quantities
* Delete harvest batches
* View available batches
* Display crop type information

## 🍽️ Restaurant Management

* Add restaurants
* Update restaurant information
* Delete restaurants
* Search restaurants by city
* View restaurant orders

## 📊 Reports & Analytics

* Top crop type report
* Top driver report
* Inactive farms report
* Restaurants without orders
* Farm revenue report
* Delivered batches report

---

# 🛠️ Technologies Used

* **Java**
* **JavaFX**
* **FXML**
* **CSS**
* **SQL Server**
* **JDBC**
* **Maven**

---

# 📂 Project Structure

```text
src/main/java/org/example
│
├── controllers
├── database
├── models
├── operations
│
└── Main.java

src/main/resources
│
├── views
└── styles
```

---

# 🖥️ GUI Features

* Modern JavaFX interface
* Interactive TableViews
* Styled components using CSS
* Multi-screen navigation
* Dynamic reports and inquiries
* Responsive desktop layout

---

# ⚙️ Database Configuration

The application uses SQL Server with JDBC connection.

Example connection:

```java
String url =
    "jdbc:sqlserver://localhost:1433;"
    + "databaseName=FarmRestaurantDB;"
    + "encrypt=true;"
    + "trustServerCertificate=true;";
```

---

# ▶️ How To Run

## 1. Clone Repository

```bash
git clone https://github.com/mohamedelgendy522/Farm-To-Restaurant-DataBase/
```

## 2. Open Project

Open the project using:

* IntelliJ IDEA

## 3. Configure Database

* Create the SQL Server database
* Import the provided SQL script
* Update database username/password if needed

## 4. Run Application

Using Maven:

```bash
mvn javafx:run
```

Or run `Main.java` directly from IntelliJ.

---

# 📸 Application Screens

* Dashboard
* Farm Management
* Driver Management
* Harvest Batch Management
* Restaurant Management
* Reports Dashboard

---

# 📈 Future Improvements

* User authentication system
* Dashboard analytics and charts
* Export reports functionality
* Advanced validation handling
* Improved responsive layouts
* Search and filtering enhancements

---

# 👨‍💻 Team Members

* Ahmed Bakr
* Ahmed Emad
* Mohammed Mahmoud
* Youssef Gomaa
* Adham Mohammed
* Mohamed Hossam

---

# 📌 Project Purpose

This project was developed as a university database management system project to demonstrate:

* Database design
* SQL query handling
* JDBC integration
* JavaFX GUI development
* CRUD operations
* Reporting systems
* Software engineering concepts
