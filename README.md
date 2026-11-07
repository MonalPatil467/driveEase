# 🚗 DriveEase — Self-Drive Car Rental Platform

**DriveEase** is a self-drive car rental platform built using Spring Boot and Angular that allows customers to browse vehicles, book cars, and manage rental bookings online. The platform connects customers and vehicle owners while providing administrators with tools to manage the system.

## 🚀 Key Features

- 🔐 **Authentication & Authorization:** JWT-based authentication with role-based access for Admins, Owners, and Customers.
- 🚘 **Vehicle Management:** Owners can manage vehicle listings and availability.
- 📅 **Booking Management:** Create and manage bookings with vehicle availability and overlapping-booking validation.
- 💳 **Online Payments:** Stripe integration for secure payment processing.
- ⭐ **Reviews & Ratings:** Support customer feedback and vehicle reviews.
- 🛡️ **Admin Management:** Administrative access to platform operations.
- 🔗 **REST APIs:** Backend APIs for communication between the frontend and backend.

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot
- **Frontend:** Angular
- **Database:** PostgreSQL
- **Security:** Spring Security, JWT
- **Payments:** Stripe
- **ORM:** Spring Data JPA, Hibernate
- **Build Tool:** Maven
- **Version Control:** Git, GitHub

## 🏗️ Architecture

DriveEase follows a client-server architecture, separating the Angular frontend from the Spring Boot backend.

```text
       Angular Frontend
              |
              v
      Spring Boot REST APIs
              |
       +------+------+
       |      |      |
       v      v      v
      Auth  Vehicle Booking
       |      |      |
       +------+------+
              |
              v
          PostgreSQL
              
       Stripe Payments
```

*Conceptual overview; adapt the diagram to match the actual implementation.*

## 👥 User Roles

| Role | Responsibilities |
|---|---|
| Admin | Manage platform administration |
| Owner | Manage vehicles and rental availability |
| Customer | Browse vehicles, book cars, and make payments |

## ⚙️ Getting Started

### Prerequisites
- Java
- Maven
- Node.js and Angular CLI
- PostgreSQL
- Stripe account and API credentials

### Installation

1. Clone the repository:

  
   git clone https://github.com/MonalPatil467/driveEase.git
   cd driveEase
   

2. Configure PostgreSQL connection details and required environment variables.

3. Start the Spring Boot backend:

   
   mvn spring-boot:run
   

4. Navigate to the Angular frontend directory, install dependencies, and start the frontend:

   
   npm install
   ng serve
   

5. Configure Stripe credentials and webhook settings according to the project's payment integration.

**Security note:** Keep database passwords, JWT secrets, and Stripe secret keys out of your GitHub repository.

## 🎯 Project Highlights

- Implemented role-based access control for multiple user types.
- Designed REST APIs for vehicle and booking workflows.
- Added booking conflict validation to prevent overlapping reservations.
- Integrated Stripe for payment processing.
- Used PostgreSQL for persistent application data.

## 👩‍💻 Developer

**Monal Patil**

B.Tech Computer Engineering | Java Backend Development

- GitHub: [MonalPatil467](https://github.com/MonalPatil467)
- Project Repository: [DriveEase](https://github.com/MonalPatil467/driveEase)

---

⭐ **DriveEase — Rent. Drive. Enjoy.**
