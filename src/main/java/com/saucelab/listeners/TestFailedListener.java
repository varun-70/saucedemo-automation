package com.saucelab.listeners;

import com.saucelab.util.ScreenshotUtil;
import io.qameta.allure.Allure;
import org.testng.*;

import java.io.ByteArrayInputStream;

public class TestFailedListener implements ITestListener, IExecutionListener, IReporter, IClassListener, ISuiteListener {
    ScreenshotUtil screenshotUtil;

    /**
     * Takes screenshot and add it to allure report
     * @param result <code>ITestResult</code> containing information about the run test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        screenshotUtil = new ScreenshotUtil();
        Allure.addAttachment("Screenshot on Failure, " + System.getProperty("browser"), new ByteArrayInputStream(screenshotUtil.takeScreenshot()));
    }
}
