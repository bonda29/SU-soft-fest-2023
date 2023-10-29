# Spring and Angular App Readme

## Prerequisites
Before you can get started, you'll need to have the following prerequisites installed on your system:

- **Java Development Kit (JDK)** - A recent version of Java (e.g., JDK 8 or later). You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or use an open-source distribution like [OpenJDK](https://openjdk.java.net/).

- **Node.js** - Angular requires Node.js, which includes npm (Node Package Manager). You can download it from [nodejs.org](https://nodejs.org/).

- **Angular CLI** - Install Angular CLI globally with `npm install -g @angular/cli`.

- **IDE** - An Integrated Development Environment (IDE) such as IntelliJ IDEA, Eclipse, or Visual Studio Code for easier development.

## Getting Started
1. Clone the repository to your local machine:

```bash
git clone https://github.com/bonda29/SU-soft-fest-2023.git
```

2. Navigate to the project directory:

```bash
cd SU-soft-fest-2023
```

## Project Structure
The project is structured into two main parts: the backend Spring application and the frontend Angular application.

- `server/`: Contains the Spring Boot application.
- `client/`: Contains the Angular application.

## Running the Application
To run the Spring backend:
1. Open your IDE and import the `server` folder as a Maven project.
2. Start the Spring Boot application.
3. Make sure that you have configered the MySQL database in application.properties.

To run the Angular frontend:
1. Open a terminal and navigate to the `client/SU-fest--2023` directory.
2. Install the required dependencies using:

```bash
npm install
```

3. Start the Angular development server:

```bash
ng serve
```

Visit `http://localhost:4200` in your web browser to access the Angular app.
