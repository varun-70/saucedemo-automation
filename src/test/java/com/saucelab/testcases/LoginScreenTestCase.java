package com.saucelab.testcases;

import com.saucelab.base.BaseTest;
import com.saucelab.constants.Constants;
import com.saucelab.pages.HomePage;
import com.saucelab.pages.LoginPage;
import com.saucelab.provider.DataProviderUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Objects;

@Epic("Login")
@Feature("Login Functionality")
public class LoginScreenTestCase extends BaseTest {
    ThreadLocal<LoginPage> login = new ThreadLocal<>();
    ThreadLocal<HomePage> homePage = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true, description = "Initialize")
    public void initialize() {
        login.set(new LoginPage(driver.get()));
        homePage.set(new HomePage(driver.get()));
    }

    @AfterMethod(alwaysRun = true, description = "Clean up page objects")
    void cleanup() {
        login.remove();
        homePage.remove();
    }

    @Test(description = "Validating Login Screen")
    public void validateLoginScreen() {
        login.get().verifyHeaderText(Constants.HEADER);
    }

    @Test(priority = 2, description = "Validating Login Credentials", dataProvider = "LoginData", dataProviderClass = DataProviderUtils.class)
    public void validateLoginCredentials(String usertype, String error, String user_id, String password) {
        switch (usertype) {
            case "Negative":
                if (Objects.equals(error, "username")) {
                    login.get().setUsernametextfield(user_id)
                            .setPasswordtextfield(password)
                            .clickLoginButton();
                    Assert.assertEquals(login.get().getError(), "Epic sadface: Username is required");
                } else if (Objects.equals(error, "incorrect_user_pass")) {
                    login.get().setUsernametextfield(user_id)
                            .setPasswordtextfield(password)
                            .clickLoginButton();
                    Assert.assertEquals(login.get().getError(), "Epic sadface: Username and password do not match any user in this service");
                } else {
                    login.get().setUsernametextfield(user_id)
                            .setPasswordtextfield(password)
                            .clickLoginButton();
                    Assert.assertEquals(login.get().getError(), "Epic sadface: Password is required");

                }

                break;
            case "Standard":
                login.get().setUsernametextfield(user_id)
                        .setPasswordtextfield(password)
                        .clickLoginButton();
                Assert.assertEquals(homePage.get().getProductSectionText(), "Products");

            default:
                System.out.println("Skipped other type of users");
        }
    }
}


