package com.saucelab.listeners;

import com.saucelab.annotaions.Login;
import com.saucelab.base.BaseTest;
import com.saucelab.constants.Constants;
import com.saucelab.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * The LoginListener class is a TestNG listener that implements the IInvokedMethodListener interface.
 *
 * This listener is responsible for intercepting test method invocations and performing a login
 * operation if the test method is annotated with @Login.
 *
 * It checks for the presence of the @Login annotation on the test method, and if found,
 * executes the login sequence before the test method is run.
 */
public class LoginListener implements IInvokedMethodListener {

    /**
     * This method is called before any test method is invoked.
     *
     * If the test method is annotated with @Login, it triggers the login process by calling
     * the private login() method.
     *
     * @param method The invoked method.
     * @param testResult The result of the test method invocation.
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && method.getTestMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(Login.class)) {
            login();
        }
    }

    /**
     * This private method handles the login process.
     *
     * It retrieves the required parameters (CampaignId, clientId, clientSecret) from the test method's parameters,
     * uses them to perform the login actions on the ToothScanSDKPage, and navigates through the login steps.
     */
    private void login() {
        WebDriver driver = BaseTest.driver.get();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUsernametextfield(Constants.STANDARD_USER);
        loginPage.setPasswordtextfield(Constants.PASSWORD);
        loginPage.tapLoginButton();
    }
}
