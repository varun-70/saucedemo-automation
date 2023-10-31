package testcases;

import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import page.LoginPage;

public class LoginScreenTestCase extends BaseClass {

   static WebDriver driver;
    LoginPage login;

    @BeforeTest
    public void setup() {
        login = new LoginPage(driver);

    }


    @Test(description = "Validating Login Screen")
    public void validateLoginScreen() {
        String currenturl = driver.getCurrentUrl();
        Assert.assertEquals(currenturl, url);
        Assert.assertEquals(login.getHeaderText(), "Swag Labs");
    }
}