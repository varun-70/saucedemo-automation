package testcases;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import pages.HomePage;
import util.DataProviderUtils;
import util.HelperUtil;
import util.ImageComparison;
import util.NavigationToScreensUtil;

import java.time.Duration;

public class CheckoutTestCase extends BaseClass {
    HomePage homePage;
    CheckoutPage checkoutPage;
    HomeScreenTestCase homeScreenTestCase;
    ImageComparison imageComparison;
    NavigationToScreensUtil navigationToScreensUtil;
    HelperUtil helperUtil;

    @BeforeClass
    void initialize() {
        homePage = new HomePage(driver);
        checkoutPage = new CheckoutPage(driver);
        homeScreenTestCase = new HomeScreenTestCase();
        imageComparison = new ImageComparison(driver);
        navigationToScreensUtil = new NavigationToScreensUtil(driver);
        helperUtil = new HelperUtil(driver);
        homeScreenTestCase.initialize();
    }

    @BeforeMethod
    void preRequisite() {
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutYourInformation);
    }


    @Test (dataProvider = "checkOutInformation", dataProviderClass = DataProviderUtils.class)
    void checkoutInformationTest(String userFlow, String error, String firstName, String lastName, String postalCode) {
        checkoutPage.setFirstNameTextField(firstName);
        checkoutPage.setLastNameTextField(lastName);
        checkoutPage.setPostalCodeTextField(postalCode);
        checkoutPage.clickContinueButton();
        if (!error.isEmpty()) {
            Assert.assertEquals(error, checkoutPage.getErrorMessage());
        }
    }

    @Test
    void checkoutNavigationTest() {
        checkoutPage.assertPageTitle(CheckoutPage.pageTitles.checkoutYourInformation);
        checkoutPage.clickCancelButton();
        checkoutPage.assertPageTitle(CheckoutPage.pageTitles.yourCart);
        checkoutPage.clickCheckoutButton();
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutOverview);
        checkoutPage.assertPageTitle(CheckoutPage.pageTitles.checkoutOverview);
        checkoutPage.clickCancelButton();
        checkoutPage.assertPageTitle(CheckoutPage.pageTitles.products);
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        checkoutPage.assertPageTitle(CheckoutPage.pageTitles.checkoutComplete);
        checkoutPage.clickBackHomeButton();
        checkoutPage.assertPageTitle(CheckoutPage.pageTitles.products);
    }

    @Test
    void paymentSummaryInfoTest() {
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutOverview);
        checkoutPage.assertPaymentInformation();
        checkoutPage.assertShippingInformation();
        checkoutPage.assertSubTotalPrice();
        checkoutPage.assertTaxPrice();
        checkoutPage.assertTotalPrice();
    }

    @Test
    void orderCompletedVisualTest() {
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        imageComparison.imageComparisonAshot(ImageComparison.imageName.checkout_complete_icon.toString(), checkoutPage.orderCompletedIcon);
        checkoutPage.clickBackHomeButton();
        imageComparison.imageComparisonAshot(ImageComparison.imageName.cart_icon_without_items_in_cart.toString(), homePage.linkShoppingCart);
    }

    @Test
    void orderCompleteTest() {
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        checkoutPage.assertPageTitle(CheckoutPage.pageTitles.checkoutComplete);
        Assert.assertEquals(checkoutPage.getOrderCompletedHeader(), "Thank you for your order!");
        Assert.assertEquals(checkoutPage.getOrderCompletedText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

    @Test
    void socialLinkNavigationTest() {
        homeScreenTestCase.socialLinkNavigationTest();
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutOverview);
        helperUtil.wait(Duration.ofSeconds(1));
        homeScreenTestCase.socialLinkNavigationTest();
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.CheckoutComplete);
        helperUtil.wait(Duration.ofSeconds(1));
        homeScreenTestCase.socialLinkNavigationTest();
    }
}
