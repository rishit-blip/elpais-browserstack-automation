package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.LoggerUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class OpinionPage {

    private WebDriver driver;

    public OpinionPage(WebDriver driver) {
        this.driver = driver;
    }

    // Open Opinion page
    public void openOpinionPage() {
//        driver.get("https://elpais.com/opinion/");
        driver.get(utils.ConfigReader.get("baseUrl"));

        LoggerUtil.log("Opened El Pais Opinion Page");
    }

    // Get first 5 VALID article links
//    public List<String> getFirstFiveArticleLinks() {
//
//        // Wait until article anchors appear
//        new WebDriverWait(driver, Duration.ofSeconds(12))
//                .until(d -> d.findElements(By.cssSelector("article a")).size() > 0);
//
//        List<WebElement> anchors = driver.findElements(By.cssSelector("article a"));
//        List<String> links = new ArrayList<>();
//
//        for (WebElement a : anchors) {
//
//            String href = a.getAttribute("href");
//
//            if (href == null) continue;
//
//            // ---- SMART FILTER ----
//            if (href.startsWith("https://elpais.com")
//                    && !href.equals("https://elpais.com/opinion/")
//                    && !href.contains("#")
//                    && !href.contains("autor")
//                    && !href.contains("archivo")
//                    && !href.contains("etiqueta")) {
//
//                if (!links.contains(href)) {
//                    links.add(href);
//                }
//            }
//
//            if (links.size() == 5) break;
//        }
//
//        LoggerUtil.log("Collected " + links.size() + " article links");
//        return links;
//    }
    public List<String> getFirstFiveArticleLinks() {

        new WebDriverWait(driver, Duration.ofSeconds(12))
                .until(d -> d.findElements(By.cssSelector("article a")).size() > 0);

        List<WebElement> anchors = driver.findElements(By.cssSelector("article a"));
        List<String> validLinks = new ArrayList<>();

        for (WebElement a : anchors) {

            String href = a.getAttribute("href");

            if (href == null) continue;

            // ----- FILTER NON-ARTICLES -----
            if (!href.startsWith("https://elpais.com")) continue;
            if (!href.contains("/opinion/")) continue;
            if (href.contains("#")) continue;
            if (href.contains("autor")) continue;
            if (href.contains("archivo")) continue;
            if (href.contains("etiqueta")) continue;
            if (href.endsWith("/")) continue;

            if (!validLinks.contains(href)) {
                validLinks.add(href);
                LoggerUtil.log("Valid article found: " + href);
            }

            if (validLinks.size() == 5) break;
        }

        LoggerUtil.log("Collected " + validLinks.size() + " VALID article links");

        return validLinks;
    }

}
