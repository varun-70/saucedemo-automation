package com.saucelab.listeners;

import com.saucelab.base.BaseTest;
import com.saucelab.constants.Constants;
import com.saucelab.pages.CartPage;
import com.saucelab.pages.HomePage;
import com.saucelab.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class SkipToCheckoutYourInformationListeners implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && method.getTestMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(com.saucelab.annotaions.SkipToCheckoutYourInformation.class)) {
            skipToCheckoutYourInformationListeners();
        }
    }

    private void skipToCheckoutYourInformationListeners() {
        WebDriver driver = BaseTest.driver.get();
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.setUsernametextfield(Constants.STANDARD_USER)
                .setPasswordtextfield(Constants.PASSWORD)
                .tapLoginButton();
        homePage.addItemsToCart(0)
                .clickShoppingCartLink();
        cartPage.clickCheckoutButton();
    }
}
