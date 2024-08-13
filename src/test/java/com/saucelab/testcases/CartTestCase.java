package com.saucelab.testcases;

import com.saucelab.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.saucelab.pages.CartPage;
import com.saucelab.pages.HomePage;
import com.saucelab.util.NavigationToScreensUtil;

public class CartTestCase extends BaseTest {
    ThreadLocal<CartPage> cartPage = new ThreadLocal<>();
    ThreadLocal<HomePage> homePage = new ThreadLocal<>();;
    ThreadLocal<HomeScreenTestCase> homeScreenTestCase = new ThreadLocal<>();;
    ThreadLocal<NavigationToScreensUtil> navigationToScreensUtil = new ThreadLocal<>();;

    @BeforeClass
    void initialize() {
        cartPage.set(new CartPage(driver.get()));
        homePage.set(new HomePage(driver.get()));
        navigationToScreensUtil.set(new NavigationToScreensUtil(driver.get()));
        homeScreenTestCase.set(new HomeScreenTestCase());
        homeScreenTestCase.get().initialize();
    }

    @BeforeMethod
    void preRequisite() {
        navigationToScreensUtil.get().navigateToScreen(NavigationToScreensUtil.screenName.YourCart);
    }

    @AfterMethod(alwaysRun = true, description = "Clean up page objects")
    void cleanup() {
        cartPage.remove();
        homePage.remove();
        homeScreenTestCase.remove();
        navigationToScreensUtil.remove();
    }

    @Test
    public void itemOrderAndPriceInCartTest() {
        cartPage.get().clickContinueShoppingButton();

        String[] itemsAddedToCart = new String[3], itemsInCart = new String[3];
        float[] itemsAddedToCartPrice = new float[3], itemsInCartPrice = new float[3];
        int[] itemElementsForAddToCart = {4,1,3}, itemElementForItemNameAndPrice = {4,1,5}; //Element of product 5,2,& 6

        for (int i=0; i<3; i++) {
            itemsAddedToCart[i] = homePage.get().getItemName(itemElementForItemNameAndPrice[i]);
            itemsAddedToCartPrice[i] = homePage.get().getItemPrice(itemElementForItemNameAndPrice[i]);
            homePage.get().clickAddToCartButton(itemElementsForAddToCart[i]);
            System.out.println(itemsAddedToCart[i] + " - " + itemsAddedToCartPrice[i]);
        }

        homePage.get().clickShoppingCartLink();

        for (int i = 0; i<3; i++) {
            itemsInCart[i] = cartPage.get().getItemName(i);
            itemsInCartPrice[i] = cartPage.get().getItemPrices(i);
            System.out.println(itemsInCart[i] + " - " + itemsInCartPrice[i]);

            Assert.assertEquals(itemsInCart[i], itemsAddedToCart[i]);
            Assert.assertEquals(itemsInCartPrice[i], itemsAddedToCartPrice[i]);
        }
    }

    @Test
    void removeItemsTest() {
        if(cartPage.get().numberOfItemsInCart() == 0) {
            cartPage.get().clickContinueShoppingButton();
            for(int i=0; i<3; i++) {
                homePage.get().clickAddToCartButton(0);
            }
        }

        for(int i=2; i>=0; i--) {
            cartPage.get().clickRemoveButton(i);
        }

        Assert.assertEquals(cartPage.get().removeButton.size(),0);
    }

    @Test
    void navigationTest() {
        cartPage.get().clickContinueShoppingButton();
        homePage.get().productsText.isDisplayed();

        homePage.get().clickItemName(0);
        homePage.get().clickShoppingCartLink();
        cartPage.get().clickContinueShoppingButton();
        homePage.get().productsText.isDisplayed();
    }

    @Test
    void socialLinkNavigationTest() {
        homeScreenTestCase.get().socialLinkNavigationTest();
    }
}
