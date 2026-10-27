# Koel Selenium Automation Framework

A Java + Selenium + TestNG UI automation framework for the Koel music player app, built with the Page Object Model.

## Overview

This project automates key user flows in the Koel web application (https://qa.koel.app), including authentication, playlist management, and profile updates. It is structured as a reusable framework that can be extended with additional page objects and test suites.

## Tech Stack

- **Java**
- **Selenium WebDriver**
- **TestNG**
- **Gradle**
- **Page Object Model**
- **Cucumber** (feature files + step definitions)

## Project Structure

```
src/
  test/
    java/
      pages/          # Page objects (LoginPage, HomePage, BasePage)
      tests/          # TestNG test classes (LoginTests, PlaylistTests, ProfileTests)
      runners/        # Cucumber runners
      stepDefinitions/# Cucumber step definitions
    resources/
      features/       # Gherkin feature files
      *.xml           # TestNG suites
```

## Features Covered

- Valid and invalid login
- Playlist create, rename, and delete
- Profile updates
- Base setup for driver management, waits, and screenshots

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
- Credentials are externalized; update them in the test classes or a config file as needed.
- The framework follows the Page Object Model for maintainability.

## Author

Daniel Vasquez  
QA Automation Engineer


## Sprint 10
Homepage, Profile & Preferences, and Current Queue automation tests.
