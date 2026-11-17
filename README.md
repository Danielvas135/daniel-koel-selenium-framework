# Koel Selenium Automation Framework

A Java + Selenium + TestNG UI automation framework for the Koel music player app, built with the Page Object Model.

## Overview
This project automates key user flows in the Koel web application (https://qa.koel.app), including authentication, playlist management, profile updates, current queue, and the info panel. It is structured as a reusable framework that can be extended with additional page objects and test suites.

I built this during a QA Automation internship and used it to verify acceptance criteria, find real bugs, and practice maintainable test design.

## Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Cucumber (BDD)
- Gradle
- Page Object Model

## Test Results
Full regression suite: **45 automated tests** (44 TestNG + 1 Cucumber BDD scenario) across 7 test classes, **100% passing** against the Koel QA environment.

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
   `git clone https://github.com/Danielvas135/daniel-koel-selenium-framework.git`
2. Open the project in IntelliJ IDEA (or any IDE with Gradle support).
3. Run the full regression suite:
   `./gradlew cleanTest test`
4. Run a single suite (for example, the smoke suite):
   `./gradlew cleanTest test -Psuite=TestNG`
5. View results in `build/reports/tests/test/index.html`.

## Notes
- Tests target the QA environment of Koel (https://qa.koel.app).
- Tests log in with a dedicated QA test account on qa.koel.app.
- The framework follows the Page Object Model for maintainability.
- Real bugs found and reported:
  - Artist shuffle on the Info Panel played the same song instead of a random one.
  - Profile name update required the current password and did not immediately reflect in the header until refreshed.
  - Some songs showed no album/artist data in the Info Panel even when expected.

## Author
Daniel Vasquez  
QA Engineer | Java · Selenium · TestNG · Cucumber
