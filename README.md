# Koel Selenium Automation Framework

A Java + Selenium + TestNG UI automation framework for the Koel music player app, built with the Page Object Model.

## Overview
This project automates key user flows in the Koel web application (https://qa.koel.app), including authentication, playlist management, profile updates, current queue, and the info panel. It is structured as a reusable framework that can be extended with additional page objects and test suites.

I built this during a QA Automation internship and used it to verify acceptance criteria, find real bugs, and practice maintainable test design.

## Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Gradle
- Page Object Model

## Project Structure
```
src/
  test/
    java/
      pages/          # Page objects (LoginPage, HomePage, BasePage)
      tests/          # TestNG test classes
    resources/
      *.xml           # TestNG suites
```

## Features Automated
- Valid and invalid login / logout
- Playlist create, rename, and delete
- Profile updates (name, email, password, theme, preferences)
- Current Queue (song list, duration, shuffle, clear)
- Info Panel (open/close, album, artist, lyrics, shuffle by artist/album)

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/Danielvas135/daniel-koel-selenium-framework.git
   ```
2. Open the project in IntelliJ IDEA (or any IDE with Gradle support).
3. Run tests with:
   ```bash
   ./gradlew test
   ```
   Or run a specific suite from the TestNG XML files in `src/test/resources`.

## Notes
- Tests target the QA environment of Koel.
- Credentials are in the test classes (update as needed).
- The framework follows the Page Object Model for maintainability.
- Several real bugs were found and reported during testing (info panel shuffle, profile update flows, etc.).

## Author
Daniel Vasquez  
QA Automation Engineer
