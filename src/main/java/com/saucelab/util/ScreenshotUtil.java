package com.saucelab.util;

import com.saucelab.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {
    WebDriver driver;

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public ScreenshotUtil() {
        driver = BaseTest.driver.get();
    }
}
