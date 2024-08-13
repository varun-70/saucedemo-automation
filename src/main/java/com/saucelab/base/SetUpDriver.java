package com.saucelab.base;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class SetUpDriver  {
    public WebDriver setUpDriver() {
        String browser = System.getProperty("browser", "firefox"); // Default to Firefox

        DriverManager webDriverManager = DriverManager.getInstance();
        webDriverManager.setDriver(browser);
        WebDriver driver = webDriverManager.getDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
}
