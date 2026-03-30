# SkillSwap Hub

SkillSwap Hub is a full-stack platform designed to foster a community of mutual learning. It allows users to exchange their professional skills and knowledge with others, creating a collaborative environment for personal and professional growth.

---

## Project Overview

The platform serves as a bridge between individuals who have expertise to share and those who are eager to learn new skills. Whether it is technical coding, creative arts, or professional communication, SkillSwap Hub manages the entire process from discovery to feedback.

## Demo and Presentation

A full video demonstration of the project, including the user workflow and admin functionalities, is available on YouTube:

**Watch Here:** [https://youtu.be/8PFF6XsyAIU](https://youtu.be/8PFF6XsyAIU)

---

## Core Functionalities

### For Users
* **Skill Profiles:** Users can list "Skills to Teach" and "Skills to Learn" to create a personal expertise profile.
* **Swap Requests:** A built-in system to send and manage incoming or outgoing skill-sharing requests.
* **Leaderboard:** Recognition of top-rated contributors based on community performance and ratings.
* **Review System:** Users can provide transparent feedback and star ratings after completing a swap.

### For Administrators
* **System Overview:** Real-time statistics showing total registered members and platform activity.
* **User Management:** Tools to view, inspect, and manage all platform members.
* **Content Control:** Management of skill categories and monitoring of user-generated reviews.

---

## Technical Stack

* **Backend:** Java Spring Boot, Spring Security (JWT), Spring Data JPA.
* **Frontend:** HTML5, CSS3, JavaScript (ES6+), jQuery.
* **Database:** MySQL.
* **Tools:** Maven, Leaflet.js (Maps integration), Git.

---

## Project Structure

* **Auth Module:** Handles secure login, registration, and role-based access control.
* **Skill Module:** Manages the repository of available skills and trending topics.
* **Review Module:** Handles the logic for calculating average ratings and storing user comments.

---

## Installation and Setup

1.  **Clone the Repository:** Download the source code to your local machine.
2.  **Database Configuration:** Set up a MySQL database and update the `application.properties` file with your credentials.
3.  **Run Backend:** Execute the Spring Boot application using Maven or your preferred IDE.
4.  **Launch Frontend:** Open `pages/login.html` to access the application.
