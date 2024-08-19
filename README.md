# ExampleLoginPage

ExampleLoginPage is a Java application that demonstrates a user interface for managing authentication and interface animations. This application uses `Swing` for the graphical user interface and `MigLayout` for layout management. It also includes animations for showing and hiding the login panel.

## Features

- **Simple Authentication**: Allows users to enter login credentials via a login panel.
- **Smooth Animations**: Animations for showing and hiding the login panel.
- **Flexible Layout**: Uses `MigLayout` for dynamic component positioning.
- **Multimedia Integration**: Includes support for video playback using `vlcj`.

## Requirements

- Java 11 or later
- `vlcj` library for video playback

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/BAlexandru99/LoginExample.git
    ```

2. Navigate to the project directory:
    ```bash
    cd LoginExample
    ```

3. Ensure all necessary dependencies are installed. If using an IDE like IntelliJ IDEA or Eclipse, import the project as a Maven project.

4. Build and run the application using Maven commands:
    ```bash
    mvn clean install
    mvn exec:java -Dexec.mainClass="com.example.Home"
    ```

## Usage

1. **Launch Application**: Run the application and you'll see the main interface with buttons and animations.
2. **Login**: Click on the "LogIn" button to display the login panel. Enter credentials to test the functionality.


