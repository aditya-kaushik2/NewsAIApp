NewsAIApp
AI Powered Fake News Detector and Summarizer

NewsAIApp is a Spring Boot based web application that uses OpenAI and Gemini APIs to summarize news articles and classify their credibility as Credible, Suspicious, or Fake. The system supports user accounts, Google OAuth2 login, password reset using JWT tokens, email notifications, and stores analysis history in MySQL.

Features
- AI based summarization of news articles
- Fake news credibility detection
- User registration and secure login
- Google OAuth2 login
- Password reset using JWT secure link
- User profile with photo upload
- Personal history of all analyzed news
- Secure backend using Spring Security
- MySQL database for persistent storage
- HTML emails for welcome and password reset

Technology Stack
- Java 17
- Spring Boot 3
- Spring MVC and Spring Security
- JPA with Hibernate
- Thymeleaf template engine
- OpenAI and Gemini APIs
- MySQL database
- Gmail SMTP for emails

How to Run the Project

1. Clone the Repository
git clone https://github.com/aditya-kaushik2/NewsAIApp.git
cd NewsAIApp

2. Configure MySQL Database
Create a database:
CREATE DATABASE newsaiapp;

Update application.properties or application.yml:
spring.datasource.url=jdbc:mysql://localhost:3306/newsaiapp
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

3. Configure Email (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

4. Add API Keys
openai.api.key=your_openai_key
gemini.api.key=your_gemini_key

5. Run the Application
Option A: Using Maven
mvn spring-boot:run

Option B: Using Java
mvn clean install
java -jar target/newsaiapp.jar

6. Open the Application
http://localhost:2222
