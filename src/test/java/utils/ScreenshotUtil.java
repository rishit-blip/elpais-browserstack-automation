package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String path = System.getProperty("user.dir")
                    + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";

            File dest = new File(path);
            dest.getParentFile().mkdirs();

            FileUtils.copyFile(src, dest);

            return path;

        } catch (Exception e) {
            return null;
        }
    }
}
