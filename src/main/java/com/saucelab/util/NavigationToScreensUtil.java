package com.saucelab.util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import com.saucelab.pages.CartPage;
import com.saucelab.pages.CheckoutPage;
import com.saucelab.pages.HomePage;
import com.saucelab.pages.LoginPage;

import java.time.Duration;

/**
 * Used to navigate to screen as pre-requisite
 */
public class NavigationToScreensUtil {

    WebDriver driver;
    LoginPage loginPage;
    CartPage cartPage;
    HomePage homePage;
    CheckoutPage checkoutPage;
    HelperUtil helperUtil;

    /**
     * To navigate any particular screen on saucedemo site
     * @param screenName takes the name of the screen
     */
    public void navigateToScreen(screenName screenName) {
        helperUtil.turnOnImplicitWaits(Duration.ofSeconds(1));
        try {
            login();
        } catch (NoSuchElementException ignored) {}

        switch (screenName) {
            case Home:
                if (!homePage.isHomePageDisplayed()) {
                    cartPage.clickContinueShoppingButton();
                }
                break;

            case YourCart:
                if (!cartPage.isYourCartDisplayed()) {
                    homePage.clickShoppingCartLink();
                }
                if (!cartPage.removeButton.isEmpty()) {
                    for (int i = 0; i < cartPage.removeButton.size(); i++)
                        cartPage.clickRemoveButton(0);
                }
                break;

            case CheckoutYourInformation:
                if (checkoutPage.pageTitleDisplayed(CheckoutPage.pageTitles.checkoutYourInformation)) {
                    break;
                }
                addProductToCartAndCheckout();
                break;

            case CheckoutOverview:
                if (checkoutPage.pageTitleDisplayed(CheckoutPage.pageTitles.checkoutOverview)) {
                    break;
                }
                addProductToCartAndCheckout();
                fillInfoFields();
                checkoutPage.clickContinueButton();
                break;


            case CheckoutComplete:
                if (checkoutPage.pageTitleDisplayed(CheckoutPage.pageTitles.checkoutComplete)) {
                    break;
                }
                addProductToCartAndCheckout();
                fillInfoFields();
                checkoutPage.clickContinueButton();
                checkoutPage.clickFinishButton();
                break;
        }

        helperUtil.turnOnImplicitWaits(Duration.ofSeconds(10));
    }

    void login() {
        loginPage.setUsernametextfield("standard_user");
        loginPage.setPasswordtextfield("secret_sauce");
        loginPage.tapLoginButton();
    }

    void addProductToCartAndCheckout() {
        try {
            checkoutPage.clickCancelButton();
        } catch (NoSuchElementException ignored) {}

        try {
            cartPage.clickContinueShoppingButton();
        } catch (NoSuchElementException ignored) {}

        try {
            checkoutPage.clickBackHomeButton();
        } catch (NoSuchElementException ignored) {}

        if (homePage.isHomePageDisplayed()) {
            homePage.addItemsToCart(0);
            homePage.clickShoppingCartLink();
            cartPage.clickCheckoutButton();
        }
    }

    void fillInfoFields() {
        checkoutPage.setFirstNameTextField("firstName");
        checkoutPage.setLastNameTextField("lastName");
        checkoutPage.setPostalCodeTextField("1234");
    }

    /** Screen names, used to send as parameter while navigating to any screen */
    public enum screenName {
        Home,
        YourCart,
        LoginScreen,
        CheckoutYourInformation,
        CheckoutOverview,
        CheckoutComplete

    }

    public NavigationToScreensUtil(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
        helperUtil = new HelperUtil(driver);
        cartPage = new CartPage(driver);
        homePage = new HomePage(driver);
        checkoutPage = new CheckoutPage(driver);
    }
}
