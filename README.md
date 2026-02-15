# El País Opinion Scraper — Selenium Automation

## Overview
This project is an automation script built using **Java + Selenium + TestNG** that demonstrates:

- Web Scraping  
- API Integration (Translation)  
- Text Processing  
- Cross-Browser Testing using BrowserStack  

The script scrapes the **Opinion section of El País (Spanish news website)**, extracts article data, translates titles to English, analyzes repeated words, and runs across multiple browsers in parallel.

---

## Features

- Scrapes first 5 articles from El País Opinion section  
- Extracts **Spanish title and content**  
- Downloads **cover image** of each article  
- Translates article titles from **Spanish → English**  
- Finds repeated words (>2 occurrences) in translated titles  
- Handles missing titles using fallback  
- Runs locally using **Headless Selenium**  
- Executes in **parallel across 5 browsers/devices using BrowserStack**  
- Auto screenshot on failure  
- Robust error handling  

---

## Technologies Used

- Java  
- Selenium WebDriver  
- TestNG  
- Maven  
- BrowserStack Automate  
- Google Translate (Public API)  
- Apache Commons IO  

---

## Project Structure

```
ElPaisAutomation/
│
├── src/main/java
│   ├── pages/
│   ├── api/
│   ├── utils/
│   ├── analysis/
│
├── src/test/java
│   ├── tests/
│   ├── browserstack/
│
├── images/
├── screenshots/
├── pom.xml
├── testng.xml
└── README.md
```

---

## How to Run Locally

1. Clone the repository  
2. Open the project in **Eclipse / IntelliJ**  
3. Run:

```
testng.xml → Run As → TestNG Suite
```

---

## BrowserStack Parallel Execution

The script runs in parallel across the following **5 browsers/devices**:

- Chrome (Windows)  
- Firefox (Windows)  
- Safari (Mac)  
- Android (Chrome)  
- iPhone (Safari)  

Parallel execution is configured in `testng.xml` using **5 TestNG threads**.

---

## Sample Output

- Spanish article titles printed  
- English translated titles printed  
- Cover images downloaded and saved in `/images`  
- Repeated word analysis displayed  
- Stable execution across multiple browsers/devices  

---

## Notes

- The script runs in **headless mode locally** and on **real devices via BrowserStack**.  
- Some external website resources (ads/analytics) may return 404 in logs — these are **not automation failures**.  
- The automation includes fallback handling for missing titles and ensures stable execution across different layouts.  

---

## Assignment Coverage

This project fulfills all required technical components:

✔ Web scraping using Selenium  
✔ API integration (Translation)  
✔ Text processing & analysis  
✔ Image downloading  
✔ Cross-browser testing  
✔ Parallel execution on BrowserStack (5 threads)  
✔ Robust automation handling  

---

## Author

**Rishit Solanki**

---
