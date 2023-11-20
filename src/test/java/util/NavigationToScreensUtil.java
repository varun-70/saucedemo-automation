package util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

/**
 * Used to navigate to screen as pre-requisite
 */
public class NavigationToScreensUtil {

    WebDriver driver;
    LoginPage loginPage;
    CartPage cartPage;
    HomePage homePage;
    HelperUtil helperUtil;

    /**
     * To navigate any screen on saucedemo site
     * @param screenName takes the name of the screen
     */
    public void navigateToScreen(screenName screenName) {
        helperUtil.turnOnImplicitWaits(Duration.ofSeconds(1));
        try {
            login();
        } catch (NoSuchElementException ignored) {}

        switch (screenName) {
            case YourCart: if (!cartPage.isYourCartDisplayed()) {
                homePage.clickShoppingCartLink();
            }
            if (!cartPage.removeButton.isEmpty()) {
                for (int i = 0; i < cartPage.removeButton.size(); i++)
                    cartPage.clickRemoveButton(0);
            }

            break;

            case Home: if(!homePage.isHomePageDisplayed()) {
                cartPage.clickContinueShoppingButton();
            }
            break;
        }

        helperUtil.turnOnImplicitWaits(Duration.ofSeconds(10));
    }

    void login() {
        loginPage.setUsernametextfield("standard_user");
        loginPage.setPasswordtextfield("secret_sauce");
        loginPage.tapLoginButton();
    }

    /** Screen names, used to send as parameter while navigating to any screen */
    public enum screenName {
        Home,
        YourCart,
        LoginScreen,
        CheckoutYourInformation,
        CheckoutOverview


    }

    public NavigationToScreensUtil(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
        helperUtil = new HelperUtil(driver);
        cartPage = new CartPage(driver);
        homePage = new HomePage(driver);
    }
}
