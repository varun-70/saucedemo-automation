package com.saucelab.base;

import com.saucelab.constants.Constants;
import com.saucelab.provider.TestDataReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.time.Duration;


public class BaseClass {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true, description = "Read test data")
    public void readTestData() {
        new TestDataReader().readFromJson();
    }

    @BeforeMethod(alwaysRun = true, description = "Launch the browser")
    public void setUp() throws IOException {
        driver.set(new SetUpDriver().setUpDriver());
        driver.get().get(Constants.URL);
    }

    @AfterMethod(alwaysRun = true, description = "Close the browser")
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
        DriverManager.getInstance().removeDriver();
    }
}


