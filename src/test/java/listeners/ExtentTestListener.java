package listeners;

import com.aventstack.extentreports.*;

import org.openqa.selenium.WebDriver;
import org.testng.*;
import utils.ScreenshotUtil;
import base.BaseTest;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = BaseTest.getDriver();

        String screenshotPath = ScreenshotUtil.capture(driver, result.getMethod().getMethodName());

        test.get().fail(result.getThrowable());

        if (screenshotPath != null) {
            try {
                test.get().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
