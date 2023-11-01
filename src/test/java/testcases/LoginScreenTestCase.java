package testcases;

import base.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import util.DataProviderUtils;

import java.util.Objects;

public class LoginScreenTestCase extends BaseClass {

    static WebDriver driver;
    LoginPage login;

    HomePage homePage;

    @BeforeMethod
    public void setup() {

        login = new LoginPage(driver);
        homePage = new HomePage(driver);


    }


    @Test(description = "Validating Login Screen")
    public void validateLoginScreen() {

        String currenturl = driver.getCurrentUrl();
        // Assert.assertEquals(currenturl, url);
        System.out.println(currenturl);
        Assert.assertEquals(login.getHeaderText(), "Swag Labs");
    }


    @Test(priority = 2, description = "Validating Login Credentials", dataProvider = "LoginData", dataProviderClass = DataProviderUtils.class)
    public void validateLoginCredentials(String usertype, String error, String user_id, String password) {
        switch (usertype) {
            case "Negative":
                if (Objects.equals(error, "username")) {
                    login.setUsernametextfield(user_id);
                    login.setPasswordtextfield(password);
                    login.tapLoginButton();
                    Assert.assertEquals(login.getError(), "Epic sadface: Username is required");
                } else if (Objects.equals(error, "incorrect_user_pass")) {
                    login.setUsernametextfield(user_id);
                    login.setPasswordtextfield(password);
                    login.tapLoginButton();
                    Assert.assertEquals(login.getError(), "Epic sadface: Username and password do not match any user in this service");
                } else {
                    login.setUsernametextfield(user_id);
                    login.setPasswordtextfield(password);
                    login.tapLoginButton();
                    Assert.assertEquals(login.getError(), "Epic sadface: Password is required");

                }

                break;
            case "Standard":
                login.setUsernametextfield(user_id);
                login.setPasswordtextfield(password);
                login.tapLoginButton();
                Assert.assertEquals(homePage.getProductSectionText(), "Products");

            default:
                System.out.println("Skipped other type of users");

        }


    }
}


