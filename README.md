# El País Opinion Scraper — Cloud-Native Selenium Automation Framework

## Overview

This project is a production-style automation framework built using **Java, Selenium WebDriver, TestNG, and Maven** that demonstrates end-to-end automation capabilities including web scraping, API integration, text processing, and cross-browser execution on BrowserStack.

The framework scrapes the **Opinion section of El País**, extracts article data, validates real articles, translates titles, performs word analysis, and executes tests across multiple browsers and devices in parallel.

---

## Key Capabilities

- Scrapes first 5 valid articles from El País Opinion section  
- Validates real articles using multi-fallback title detection  
- Extracts Spanish titles and article content  
- Downloads article cover images  
- Translates titles from Spanish → English  
- Performs repeated word frequency analysis  
- Handles missing titles using robust fallback logic  
- Runs locally using Headless Selenium  
- Executes in parallel across 5 browsers/devices on BrowserStack  
- Supports Maven command line execution  
- Uses configuration-driven execution  
- Automatically captures screenshots on failures  
- Includes logging and reporting utilities  

---

## Architecture Highlights

This framework follows industry automation design principles:

- Page Object Model (POM)  
- Config-driven execution  
- Separation of test logic and utilities  
- Parallel execution support  
- Cloud execution capability  
- Robust error handling  
- Modular and scalable structure  

---

## Technologies Used

- Java  
- Selenium WebDriver  
- TestNG  
- Maven  
- BrowserStack Automate  
- Google Translate Public API  
- Apache Commons IO  
- Extent Reports  
- WebDriverManager  

---

## Project Structure

```
elpais-browserstack-automation/
│
├── src/main/java
│   ├── analysis/        # Word frequency analysis
│   ├── api/             # Translation API integration
│   ├── pages/           # Page Object Models
│   ├── utils/           # Logger, config reader, helpers
│
├── src/test/java
│   ├── base/            # WebDriver setup & teardown
│   ├── browserstack/    # Cross-browser execution
│   ├── listeners/       # Reporting listeners
│   ├── tests/           # Test scenarios
│   ├── utils/           # Screenshot utilities
│
├── src/test/resources
│   ├── config.properties
│
├── target/
├── bs-local.xml
├── bs-parallel.xml
├── pom.xml
└── README.md

---

## Configuration File

Execution is controlled using `config.properties`.

### Location

```
src/test/resources/config.properties
```

### Example

```
baseUrl=https://elpais.com/opinion/
headless=false
browserstack=true

bs.username=YOUR_USERNAME
bs.key=YOUR_ACCESS_KEY
```

---

## Article Validation Logic

The framework ensures only real articles are processed by using multiple fallback strategies to extract titles.

This prevents scraping empty or invalid pages.

### Title Detection Strategy

1. Standard H1  
2. Header H1  
3. Specific El País class  
4. Meta tag (og:title)  
5. JavaScript document.title  

This makes scraping resilient to layout variations.

---

## How to Run Locally (Eclipse / IntelliJ)

Run the local TestNG suite:

```
bs-local.xml → Run As → TestNG Suite
```

---

## Run Using Maven (Command Line)

Navigate to project root and run:

```
mvn clean test
```

This executes tests using Maven lifecycle.

---

## BrowserStack Parallel Execution

The framework runs tests across 5 environments in parallel:

- Chrome — Windows  
- Firefox — Windows  
- Safari — macOS  
- Android — Chrome  
- iPhone — Safari  

Parallel execution is configured using TestNG threads.

### Run Parallel Suite

```
bs-parallel.xml → Run As → TestNG Suite
```

---

## Execution Modes

### Local Mode
- Runs using ChromeDriver
- Supports headless execution
- Faster feedback loop

### Cloud Mode (BrowserStack)
- Real devices and browsers
- Parallel execution
- Cross-platform validation

---

## Sample Output

- Spanish titles printed in logs  
- English translated titles displayed  
- Images downloaded to `/images`  
- Word frequency analysis printed  
- Execution logs with status  

---

## Assignment Coverage

This project demonstrates all required technical competencies:

✔ Web scraping using Selenium  
✔ API integration for translation  
✔ Text processing and analysis  
✔ Image downloading  
✔ Cross-browser testing  
✔ Parallel execution on BrowserStack  
✔ Config-driven framework  
✔ Maven execution support  
✔ Robust fallback logic  
✔ Cloud execution capability  

---

## Design Considerations

- Handles dynamic page rendering delays  
- Prevents failures from missing elements  
- Ignores external resource errors (ads/analytics)  
- Ensures stable execution across layouts  
- Supports scalable framework expansion  

---

## Value Proposition

This framework demonstrates how automation can:

- Validate content across browsers  
- Extract and process real-world data  
- Integrate external APIs  
- Execute reliably in cloud environments  
- Scale with modular architecture  

---

## Author

Rishit Solanki  

---

## Final Notes

This framework is designed to simulate real-world automation scenarios with emphasis on reliability, scalability, and cross-platform execution.


