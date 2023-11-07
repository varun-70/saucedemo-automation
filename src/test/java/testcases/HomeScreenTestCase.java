package testcases;

import base.BaseClass;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import util.HelperUtil;
import util.ImageComparison;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomeScreenTestCase extends BaseClass {
    HomePage homePage;
    HelperUtil helperUtil;
    SoftAssert softAssert;
    ImageComparison imageComparison;

    @BeforeClass
    void initialize() {
        homePage = new HomePage(driver);
        helperUtil = new HelperUtil(driver);
        softAssert = new SoftAssert();
        imageComparison = new ImageComparison(driver);
    }

    @BeforeMethod
    void preRequisite() {
        helperUtil.turnOnImplicitWaits(Duration.ofSeconds(1));
        if (!homePage.isHomePageDisplayed()) {
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
        }
        helperUtil.turnOnImplicitWaits(Duration.ofSeconds(10));
    }

    /** To perform visual assertion on the shopping cart with and without any items in cart, and social links */
    @Test
    public void visualTest() {
        imageComparison.imageComparisonAshot(ImageComparison.imageName.cart_icon_without_items_in_cart.toString(), homePage.linkShoppingCart);
        homePage.clickAddToCartButton(1);
        imageComparison.imageComparisonAshot(ImageComparison.imageName.cart_icon_with_one_item.toString(), homePage.linkShoppingCart);
        imageComparison.imageComparisonAshot(ImageComparison.imageName.twitter_social_icon.toString(), homePage.twitterSocialLink);
        imageComparison.imageComparisonAshot(ImageComparison.imageName.facebook_social_icon.toString(), homePage.facebookSocialLink);
        imageComparison.imageComparisonAshot(ImageComparison.imageName.linkedin_social_icon.toString(), homePage.linkedInSocialLink);
    }

    @Test
    public void addToCartTest() {
        int addToCartButtonsCount = homePage.addToCartButton.size();
        for (int i = 0; i < addToCartButtonsCount; i++) {
            homePage.clickAddToCartButton(1);
        }

        homePage.clickShoppingCartLink();
        Assert.assertEquals(homePage.cartQuantity.size(), addToCartButtonsCount);
        homePage.clickContinueShoppingButton();

        int removeButtonCount = homePage.removeButton.size();
        for (int i = 0; i < removeButtonCount; i++) {
            homePage.clickRemoveButton(1);
        }
        homePage.clickShoppingCartLink();
        Assert.assertEquals(homePage.cartQuantity.size(), 0);
        homePage.clickContinueShoppingButton();
    }

    @Test
    public void directUrlNavigationTest() {
        driver.manage().deleteAllCookies();
        driver.get("https://www.saucedemo.com/inventory.html");
        homePage.assertWrongNavigationError();
    }

    @Test
    public void sorting() {
        homePage.setSortingDropDown(HomePage.sorting.Name_A_to_Z);
        Assert.assertTrue(homePage.assertSortByItemName(HomePage.sorting.Name_A_to_Z));

        homePage.setSortingDropDown(HomePage.sorting.Name_Z_to_A);
        Assert.assertTrue(homePage.assertSortByItemName(HomePage.sorting.Name_Z_to_A));

        homePage.setSortingDropDown(HomePage.sorting.Price_low_to_high);
        Assert.assertTrue(homePage.asserSortByPrice(HomePage.sorting.Price_low_to_high));

        homePage.setSortingDropDown(HomePage.sorting.Price_high_to_low);
        Assert.assertTrue(homePage.asserSortByPrice(HomePage.sorting.Price_high_to_low));
    }

    @Test
    public void socialLinkNavigationTest() {
        homePage.clickTwitterSocialLink();
        assertSocialLinkNavigationTest("https://twitter.com/saucelabs");

        homePage.clickFacebookSocialLink();
        assertSocialLinkNavigationTest("https://www.facebook.com/saucelabs");

        homePage.clickLinkedInSocialLink();
        assertSocialLinkNavigationTest("https://www.linkedin.com/company/sauce-labs/");
    }

    @Test
    public void copyRightLabelTest() {
        homePage.assertCopyRightLabel();
    }

    @Test(enabled = false)
    public void performanceTest() {
        // Write your code here
    }

    void assertSocialLinkNavigationTest(String expectedLink) {
        List<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        helperUtil.wait(Duration.ofSeconds(2));

        Assert.assertEquals(driver.getCurrentUrl(), expectedLink);
        driver.close();
        driver.switchTo().window(browserTabs.get(0));
    }
}
