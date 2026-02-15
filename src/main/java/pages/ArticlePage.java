package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.LoggerUtil;

public class ArticlePage {

    WebDriver driver;

    public ArticlePage(WebDriver driver) {
        this.driver = driver;
    }

//    public String openArticleAndGetTitle(String url) {
//
//        driver.get(url);
//
//        try {
//
//            Thread.sleep(1500);
//
//            List<WebElement> titles = driver.findElements(By.cssSelector("h1"));
//
//            if (!titles.isEmpty()) {
//
//                String title = titles.get(0).getText().trim();
//
//                if (!title.isEmpty()) {
//                	LoggerUtil.log("\nSpanish Title: " + title);
//                    return title;
//                }
//            }
//
//            LoggerUtil.log("\nSpanish Title not found, using fallback");
//            return "No Title Found";
//
//        } catch (Exception e) {
//
//        	LoggerUtil.log("\nFailed to extract title");
//            return "No Title Found";
//        }
    public String openArticleAndGetTitle(String url) {

        driver.get(url);

        String title = "";

        // Wait a bit for page render (important for dynamic pages)
        try { Thread.sleep(1200); } catch (Exception ignored) {}

        // 1️⃣ Normal H1
        try {
            title = driver.findElement(By.cssSelector("h1")).getText();
        } catch (Exception ignored) {}

        // 2️⃣ Header H1
        if (title == null || title.isEmpty()) {
            try {
                title = driver.findElement(By.cssSelector("header h1")).getText();
            } catch (Exception ignored) {}
        }

        // 3️⃣ Specific class used by ElPais
        if (title == null || title.isEmpty()) {
            try {
                title = driver.findElement(By.cssSelector("h1.a_t")).getText();
            } catch (Exception ignored) {}
        }

        // 4️⃣ META TAG fallback (VERY IMPORTANT — fixes remaining cases)
        if (title == null || title.isEmpty()) {
            try {
                title = driver.findElement(By.cssSelector("meta[property='og:title']"))
                              .getAttribute("content");
            } catch (Exception ignored) {}
        }

        // 5️⃣ FINAL fallback using JavaScript
        if (title == null || title.isEmpty()) {
            try {
                title = (String) ((JavascriptExecutor) driver)
                        .executeScript("return document.title;");
            } catch (Exception ignored) {}
        }

        if (title == null || title.trim().isEmpty()) {
            LoggerUtil.log("Spanish Title still not found (very rare)");
            return "No Title Found";
        }

        LoggerUtil.log("\nSpanish Title: " + title.trim());
        return title.trim();
    }



    public String getArticleContent() {

        StringBuilder content = new StringBuilder();

        try {

            List<WebElement> paragraphs = driver.findElements(By.cssSelector("article p"));

            for (WebElement p : paragraphs) {

                try {
                    String text = p.getText();

                    if (!text.isEmpty()) {
                        content.append(text).append("\n");
                    }

                } catch (StaleElementReferenceException e) {
                    // Ignore stale paragraph and continue
                }
            }

            LoggerUtil.log("Article Content Extracted");

        } catch (Exception e) {
        	LoggerUtil.log("Failed to extract content");
        }

        return content.toString();
    }


    public String getCoverImageUrl() {

        try {
            WebElement img = driver.findElement(By.cssSelector("figure img"));
            String imgUrl = img.getAttribute("src");

            if (imgUrl != null && !imgUrl.isEmpty()) {
            	LoggerUtil.log("Image Found");
                return imgUrl;
            }

        } catch (Exception e) {
        	LoggerUtil.log("No Image Found");
        }

        return null;
    }
}

