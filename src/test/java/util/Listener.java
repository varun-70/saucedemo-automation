package util;

import base.BaseClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;
import util.extentReport.ExtentReportTestNG;

import java.io.File;

public class Listener extends BaseClass implements ITestListener, IExecutionListener, IReporter, IClassListener, ISuiteListener {
    ExtentReports extent;
    public static ExtentTest test;
    public static ExtentTest node;

    @Override
    public void onExecutionStart() {
        extent = ExtentReportTestNG.getReporterObject();
    }

    @Override
    public void onExecutionFinish() {
        extent.flush();
    }

    @Override
    public void onBeforeClass(ITestClass result) {
        test = extent.createTest(result.getRealClass().getSimpleName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        node = test.createNode(result.getMethod().getMethodName());
    }

    /**
     * Takes the screenshot and attached it to the report.html file
     *
     * @param result <code>ITestResult</code> containing information about the run test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String encodedScreenshotInString = ExtentReportTestNG.encodeFileToBase64Binary(screenshotFile);
        node.log(Status.FAIL, result.getThrowable())
                .addScreenCaptureFromBase64String(encodedScreenshotInString);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        node.log(Status.PASS, "Test Passed");
    }
}
