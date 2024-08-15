package com.saucelab.testcases;

import com.saucelab.annotations.Login;
import com.saucelab.base.BaseTest;
import com.saucelab.constants.Constants;
import com.saucelab.pages.HomePage;
import com.saucelab.util.ImageComparison;
import com.saucelab.util.NavigationToScreensUtil;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Home")
public class HomeScreenTestCase extends BaseTest {
    ThreadLocal<HomePage> homePage = new ThreadLocal<>();
    ThreadLocal<ImageComparison> imageComparison = new ThreadLocal<>();
    ThreadLocal<NavigationToScreensUtil> navigationToScreensUtil = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true, description = "Initialize")
    void initialize() {
        homePage.set(new HomePage(driver.get()));
        imageComparison.set(new ImageComparison(driver.get()));
        navigationToScreensUtil.set(new NavigationToScreensUtil(driver.get()));
    }

    @AfterMethod(alwaysRun = true, description = "Clean up page objects")
    void cleanup() {
        homePage.remove();
        imageComparison.remove();
        navigationToScreensUtil.remove();
    }

    /** To perform visual assertion on the shopping cart with and without any items in cart, and social links */
    @Test(description = "Visual image comparison test")
    @Login
    public void visualTest() {
        imageComparison.get().imageComparisonAshot(ImageComparison.imageName.cart_icon_without_items_in_cart.toString(), homePage.get().linkShoppingCart);
        homePage.get().addItemsToCart(0);
        imageComparison.get().imageComparisonAshot(ImageComparison.imageName.cart_icon_with_one_item.toString(), homePage.get().linkShoppingCart);
        imageComparison.get().imageComparisonAshot(ImageComparison.imageName.twitter_social_icon.toString(), homePage.get().twitterSocialLink);
        imageComparison.get().imageComparisonAshot(ImageComparison.imageName.facebook_social_icon.toString(), homePage.get().facebookSocialLink);
        imageComparison.get().imageComparisonAshot(ImageComparison.imageName.linkedin_social_icon.toString(), homePage.get().linkedInSocialLink);
    }

    @Test(description = "Add to cart test")
    @Login
    public void addToCartTest() {
        int addToCartButtonsCount = homePage.get().addToCartButton.size();
        for (int i = 0; i < addToCartButtonsCount; i++) {
            homePage.get().addItemsToCart(0);
        }

        homePage.get().clickShoppingCartLink();
        Assert.assertEquals(homePage.get().cartQuantity.size(), addToCartButtonsCount);
        homePage.get().clickContinueShoppingButton();

        int removeButtonCount = homePage.get().removeButton.size();
        for (int i = 0; i < removeButtonCount; i++) {
            homePage.get().clickRemoveButton(1);
        }
        homePage.get().clickShoppingCartLink();
        Assert.assertEquals(homePage.get().cartQuantity.size(), 0);
        homePage.get().clickContinueShoppingButton();
    }

    @Test(description = "Direct URL navigation test")
    @Login
    public void directUrlNavigationTest() {
        driver.get().manage().deleteAllCookies();
        driver.get().get("https://www.saucedemo.com/inventory.html");
        homePage.get().assertWrongNavigationError();
    }

    @Test(description = "Verify sorting product items on home")
    @Login
    public void sorting() {
        homePage.get().setSortingDropDown(HomePage.sorting.Name_A_to_Z)
                .assertSortByItemName(HomePage.sorting.Name_A_to_Z)
                .setSortingDropDown(HomePage.sorting.Name_Z_to_A)
                .assertSortByItemName(HomePage.sorting.Name_Z_to_A)
                .setSortingDropDown(HomePage.sorting.Price_low_to_high)
                .asserSortByPrice(HomePage.sorting.Price_low_to_high)
                .setSortingDropDown(HomePage.sorting.Price_high_to_low)
                .asserSortByPrice(HomePage.sorting.Price_high_to_low);
    }

    @Test(description = "Social link navigation test")
    @Login
    public void socialLinkNavigationTest() {
        homePage.get().clickTwitterSocialLink()
                .verifySocialLinkNavigationTest(Constants.TWITTER_LINK)
                .clickFacebookSocialLink()
                .verifySocialLinkNavigationTest(Constants.FACEBOOK_LINK)
                .clickLinkedInSocialLink()
                .verifySocialLinkNavigationTest(Constants.LINKEDIN_LINK);
    }

    @Test(description = "Verify copy right label test")
    @Login
    public void copyRightLabelTest() {
        homePage.get().assertCopyRightLabel();
    }

    @Test(enabled = false)
    @Login
    public void performanceTest() {
        // Write your code here
    }
}
