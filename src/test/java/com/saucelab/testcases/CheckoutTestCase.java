package com.saucelab.testcases;

import com.saucelab.annotations.SkipToCheckoutYourInformation;
import com.saucelab.base.BaseTest;
import com.saucelab.pages.CheckoutPage;
import com.saucelab.pages.HomePage;
import com.saucelab.provider.DataProviderUtils;
import com.saucelab.util.ImageComparison;
import com.saucelab.util.NavigationToScreensUtil;
import io.qameta.allure.Epic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Checkout")
public class CheckoutTestCase extends BaseTest {
    ThreadLocal<HomePage> homePage = new ThreadLocal<>();
    ThreadLocal<CheckoutPage> checkoutPage = new ThreadLocal<>();
    ThreadLocal<HomeScreenTestCase> homeScreenTestCase = new ThreadLocal<>();
    ThreadLocal<ImageComparison> imageComparison = new ThreadLocal<>();
    ThreadLocal<NavigationToScreensUtil> navigationToScreensUtil = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true, description = "Initialize")
    void initialize() {
        homePage.set(new HomePage(driver.get()));
        checkoutPage.set(new CheckoutPage(driver.get()));
        homeScreenTestCase.set(new HomeScreenTestCase());
        imageComparison.set(new ImageComparison(driver.get()));
        navigationToScreensUtil.set(new NavigationToScreensUtil(driver.get()));
        homeScreenTestCase.get().initialize();
    }

    @AfterMethod(alwaysRun = true, description = "Clean up page objects")
    void cleanup() {
        homePage.remove();
        checkoutPage.remove();
        homeScreenTestCase.remove();
        imageComparison.remove();
        navigationToScreensUtil.remove();
    }

    @Test (dataProvider = "checkOutInformation", dataProviderClass = DataProviderUtils.class
            , description = "Verify checkout information fields test")
    @SkipToCheckoutYourInformation
    void checkoutInformationTest(String userFlow, String error, String firstName, String lastName, String postalCode) {
        checkoutPage.get().setFirstNameTextField(firstName)
                .setLastNameTextField(lastName)
                .setPostalCodeTextField(postalCode)
                .clickContinueButton();
        if (!error.isEmpty()) {
            checkoutPage.get().verifyErrorMessage(error);
        }
    }

    @Test(description = "Verify check out navigation test")
    @SkipToCheckoutYourInformation
    void checkoutNavigationTest() {
        checkoutPage.get().assertPageTitle(CheckoutPage.pageTitles.checkoutYourInformation)
                .clickCancelButton()
                .assertPageTitle(CheckoutPage.pageTitles.yourCart)
                .clickCheckoutButton();
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.CheckoutOverview);
        checkoutPage.get().assertPageTitle(CheckoutPage.pageTitles.checkoutOverview)
                .clickCancelButton()
                .assertPageTitle(CheckoutPage.pageTitles.products);
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        checkoutPage.get().assertPageTitle(CheckoutPage.pageTitles.checkoutComplete)
                .clickBackHomeButton()
                .assertPageTitle(CheckoutPage.pageTitles.products);
    }

    @Test(description = "Verify payment summary info test")
    @SkipToCheckoutYourInformation
    void paymentSummaryInfoTest() {
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.CheckoutOverview);
        checkoutPage.get().assertPaymentInformation()
                .assertShippingInformation()
                .assertSubTotalPrice()
                .assertTaxPrice()
                .assertTotalPrice();
    }

    @Test(description = "Verify order complete visual test")
    @SkipToCheckoutYourInformation
    void orderCompletedVisualTest() {
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        imageComparison.get().imageComparisonAshot(ImageComparison.imageName.checkout_complete_icon.toString(), checkoutPage.get().orderCompletedIcon);
        checkoutPage.get().clickBackHomeButton();
        imageComparison.get().imageComparisonAshot(ImageComparison.imageName.cart_icon_without_items_in_cart.toString(), homePage.get().linkShoppingCart);
    }

    @Test(description = "Verify order complete test")
    @SkipToCheckoutYourInformation
    void orderCompleteTest() {
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        checkoutPage.get().assertPageTitle(CheckoutPage.pageTitles.checkoutComplete)
                .verifyOrderCompletedHeader("Thank you for your order!")
                .verifyOrderCompletedHeader("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

    @Test(description = "Verify social link navigation test")
    @SkipToCheckoutYourInformation
    void socialLinkNavigationTest() {
        homeScreenTestCase.get().socialLinkNavigationTest();
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.CheckoutOverview);
        homeScreenTestCase.get().socialLinkNavigationTest();
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        homeScreenTestCase.get().socialLinkNavigationTest();
    }
}
