package base;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;
import utils.LoggerUtil;

public class BaseTest {

    protected WebDriver driver;
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("local") String browser) throws Exception {

        LoggerUtil.log("========= ENTERING SETUP =========");
        LoggerUtil.log("Browser = " + browser);

        // ⭐ LOCAL EXECUTION BLOCK ⭐
        if (browser.equalsIgnoreCase("local")) {

            LoggerUtil.log("Running locally on Chrome");

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            if (ConfigReader.get("headless").equalsIgnoreCase("true")) {
                options.addArguments("--headless=new");
            }

            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(options);

        } else {

            // ⭐ BROWSERSTACK BLOCK ⭐
            String USERNAME = ConfigReader.get("bs.username");
            String ACCESS_KEY = ConfigReader.get("bs.key");

            LoggerUtil.log("USERNAME = " + USERNAME);

            MutableCapabilities caps = new MutableCapabilities();
            Map<String, Object> bstackOptions = new HashMap<>();

            bstackOptions.put("projectName", "ElPais Scraper");
            bstackOptions.put("buildName", "ElPais Assignment");
            bstackOptions.put("sessionName", browser);

            if (browser.equalsIgnoreCase("chrome")) {
                caps.setCapability("browserName", "chrome");
                caps.setCapability("browserVersion", "latest");
                bstackOptions.put("os", "Windows");
                bstackOptions.put("osVersion", "11");
            }

            else if (browser.equalsIgnoreCase("firefox")) {
                caps.setCapability("browserName", "firefox");
                caps.setCapability("browserVersion", "latest");
                bstackOptions.put("os", "Windows");
                bstackOptions.put("osVersion", "11");
            }

            else if (browser.equalsIgnoreCase("safari")) {
                caps.setCapability("browserName", "safari");
                caps.setCapability("browserVersion", "latest");
                bstackOptions.put("os", "OS X");
                bstackOptions.put("osVersion", "Sonoma");
            }

            else if (browser.equalsIgnoreCase("android")) {
                caps.setCapability("browserName", "chrome");
                bstackOptions.put("deviceName", "Samsung Galaxy S23");
                bstackOptions.put("osVersion", "13.0");
                bstackOptions.put("realMobile", "true");
            }

            else if (browser.equalsIgnoreCase("iphone")) {
                caps.setCapability("browserName", "safari");
                bstackOptions.put("deviceName", "iPhone 15");
                bstackOptions.put("osVersion", "17");
                bstackOptions.put("realMobile", "true");
            }

            caps.setCapability("bstack:options", bstackOptions);

            LoggerUtil.log("Connecting to BrowserStack...");

            driver = new RemoteWebDriver(
                    new URL("https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub"),
                    caps);

            LoggerUtil.log("Session created successfully");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        threadDriver.set(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName());
        }

        if (driver != null) {
            driver.quit();
            LoggerUtil.log("Browser closed");
        }

        threadDriver.remove();
    }

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    public void takeScreenshot(String testName) throws IOException {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/" + testName + ".png");

        dest.getParentFile().mkdirs();
        FileUtils.copyFile(src, dest);

        LoggerUtil.log("Screenshot saved: " + dest.getAbsolutePath());
    }

    protected void sendBrowserStackStatus(String status, String reason) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "browserstack_executor: {\"action\": \"setSessionStatus\", " +
                            "\"arguments\": {\"status\": \"" + status + "\", \"reason\": \"" + reason + "\"}}"
            );
        } catch (Exception ignored) {}
    }
}

