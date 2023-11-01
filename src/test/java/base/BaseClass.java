package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.time.Duration;


public class BaseClass {

    public static WebDriver driver;
    public String url;

    @BeforeMethod
    public void setUp() throws IOException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        url = "https://www.saucedemo.com/";
        driver.get(url);

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}


