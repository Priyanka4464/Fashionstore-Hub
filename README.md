# FashionStore – E-Commerce Web Application

## Overview

FashionStore is a full-stack e-commerce web application developed using Java technologies. The application allows users to browse fashion products, manage shopping carts, register/login securely, and place orders through a user-friendly interface.

## Features

* User Registration and Login Authentication
* Product Browsing and Management
* Shopping Cart Functionality
* Order Placement and Tracking
* MVC Architecture Implementation
* Database Connectivity using JDBC
* Responsive and User-Friendly Interface

## Technologies Used

* Java
* JSP
* Servlets
* JDBC
* MySQL
* Maven
* Apache Tomcat
* HTML, CSS, JavaScript

## Project Architecture

The project follows the MVC (Model-View-Controller) architecture:

* **Model:** Handles database operations and business logic
* **View:** JSP pages for frontend UI
* **Controller:** Servlets for request handling and navigation

## Installation & Setup

### Prerequisites

* Java JDK 8 or above
* Apache Tomcat Server
* MySQL Database
* Maven

### Steps to Run the Project

1. Clone the repository:

   ```bash
   git clone <repository-link>
   ```

2. Import the project into Eclipse/IntelliJ as a Maven project.

3. Configure MySQL database:

   * Create a database named `fashionstore`
   * Import the SQL file if available

4. Update database credentials in the configuration file.

5. Build the project using Maven:

   ```bash
   mvn clean install
   ```

6. Deploy the project on Apache Tomcat Server.

7. Open the browser and run:

   ```bash
   http://localhost:8080/FashionStore
   ```

## Modules

* User Module
* Admin Module
* Product Management
* Cart Management
* Order Management

## Future Enhancements

* Online Payment Gateway Integration
* Product Search and Filtering
* Wishlist Feature
* Email Notifications
* Mobile Responsive Design

## Author

Priyanka K
