
# Cat Game Website

Cat Game Website is a Spring Boot-based web application that offers a fun game where users can test their clicking speed by clicking on a cat within a 30-second time limit. The game tracks scores, displays high scores, and allows users to compete against themselves or others.

## Features

- **Interactive Game**: A simple game where users must click on a cat as many times as possible within 30 seconds.
- **User Authentication**: Secure user registration and login using Spring Security.
- **Two-Factor Authentication (2FA)**: Optional 2FA for added security during login.
- **Score Tracking**: The application records and displays the user’s highest score.
- **User Management**: Users must register and log in to access the game.
- **Password Recovery**: Users can request a password reset link via email if they forget their password.
- **Email Notifications**: Sends an email with a password reset link using Gmail (or other mail providers).
## Technologies Used

- `Java`
- `Spring Boot`
- `Spring Security`
- `JavaScript`
- `Maven`
- `MySQL (for user and highscore storage)`
- `Gmail API (for email notifications, configurable for other providers)`

## Installation and Setup

1. Clone the repository:

    ´´´bash
    git clone https://github.com/your-username/cat-game-wesbite.git

2. Navigate to the project directory:

    ´´´bash
    cd cat-game-website

3. Configure MySQL Database:

- Create a file named secret.properties in the src/main/resources directory with the following content:

db.url=jdbc:mysql://localhost:3306/your-database-name
db.username=your-username
db.password=your-password

- Ensure your application.properties imports secret.properties:

spring.config.import=secret.properties

4. Configure Email Service:

- Create a file named application.yml in the src/main/resources directory with the following content:

spring:
  mail:
    host: smtp.gmail.com
    username: your-email@gmail.com
    password: your-app-password
    port: 587
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

5. Build and Run the Application:

´´´bash
mvn spring-boot:run

6. Access the Application:

Open a browser and navigate to http://localhost:8080.

## How to Play
1. Register for an account or log in if you already have one.
2. If you choose, enable Two-Factor Authentication (2FA) for added security.
3. Start the game and click the cat as many times as possible within 30 seconds.
4. Check your score and see if you've beaten your high score!

## Future Improvements
- Add more levels or difficulty settings.
- Implement leaderboard functionality to compare scores globally.
- Improve the UI/UX for a better user experience.

Make sure to replace your-email@gmail.com, your-app-password, and your-database-name with your actual credentials and database details.