package testcases;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import util.NavigationToScreensUtil;

public class CartTestCase extends BaseClass {
    CartPage cartPage;
    HomePage homePage;
    HomeScreenTestCase homeScreenTestCase;
    NavigationToScreensUtil navigationToScreensUtil;

    @BeforeClass
    void initialize() {
        cartPage = new CartPage(driver);
        homePage = new HomePage(driver);
        navigationToScreensUtil = new NavigationToScreensUtil(driver);
        homeScreenTestCase = new HomeScreenTestCase();
        homeScreenTestCase.initialize();
    }

    @BeforeMethod
    void preRequisite() {
        navigationToScreensUtil.navigateToScreen(NavigationToScreensUtil.screenName.YourCart);
    }

    @Test
    public void itemOrderAndPriceInCartTest() {
        cartPage.clickContinueShoppingButton();

        String[] itemsAddedToCart = new String[3], itemsInCart = new String[3];
        float[] itemsAddedToCartPrice = new float[3], itemsInCartPrice = new float[3];
        int[] itemElementsForAddToCart = {4,1,3}, itemElementForItemNameAndPrice = {4,1,5}; //Element of product 5,2,& 6

        for (int i=0; i<3; i++) {
            itemsAddedToCart[i] = homePage.getItemName(itemElementForItemNameAndPrice[i]);
            itemsAddedToCartPrice[i] = homePage.getItemPrice(itemElementForItemNameAndPrice[i]);
            homePage.clickAddToCartButton(itemElementsForAddToCart[i]);
            System.out.println(itemsAddedToCart[i] + " - " + itemsAddedToCartPrice[i]);
        }

        homePage.clickShoppingCartLink();

        for (int i = 0; i<3; i++) {
            itemsInCart[i] = cartPage.getItemName(i);
            itemsInCartPrice[i] = cartPage.getItemPrices(i);
            System.out.println(itemsInCart[i] + " - " + itemsInCartPrice[i]);

            Assert.assertEquals(itemsInCart[i], itemsAddedToCart[i]);
            Assert.assertEquals(itemsInCartPrice[i], itemsAddedToCartPrice[i]);
        }
    }

    @Test
    void removeItemsTest() {
        if(cartPage.numberOfItemsInCart() == 0) {
            cartPage.clickContinueShoppingButton();
            for(int i=0; i<3; i++) {
                homePage.clickAddToCartButton(0);
            }
        }

        for(int i=2; i>=0; i--) {
            cartPage.clickRemoveButton(i);
        }

        Assert.assertEquals(cartPage.removeButton.size(),0);
    }

    @Test
    void navigationTest() {
        cartPage.clickContinueShoppingButton();
        homePage.productsText.isDisplayed();

        homePage.clickItemName(0);
        homePage.clickShoppingCartLink();
        cartPage.clickContinueShoppingButton();
        homePage.productsText.isDisplayed();
    }

    @Test
    void socialLinkNavigationTest() {
        homeScreenTestCase.socialLinkNavigationTest();
    }
}
