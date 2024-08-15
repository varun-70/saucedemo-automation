package com.saucelab.testcases;

import com.saucelab.base.BaseTest;
import com.saucelab.pages.CartPage;
import com.saucelab.pages.HomePage;
import com.saucelab.util.NavigationToScreensUtil;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Cart")
public class CartTestCase extends BaseTest {
    ThreadLocal<CartPage> cartPage = new ThreadLocal<>();
    ThreadLocal<HomePage> homePage = new ThreadLocal<>();
    ThreadLocal<HomeScreenTestCase> homeScreenTestCase = new ThreadLocal<>();
    ThreadLocal<NavigationToScreensUtil> navigationToScreensUtil = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true, description = "Initialize")
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

    @Test(description = "Verify product items order and there price on the cart")
    public void itemOrderAndPriceInCartTest() {
        cartPage.get().clickContinueShoppingButton();

        String[] itemsAddedToCart = new String[3], itemsInCart = new String[3];
        float[] itemsAddedToCartPrice = new float[3], itemsInCartPrice = new float[3];
        int[] itemElementsForAddToCart = {4,1,3}, itemElementForItemNameAndPrice = {4,1,5}; //Element of product 5,2,& 6

        for (int i=0; i<3; i++) {
            itemsAddedToCart[i] = homePage.get().getItemName(itemElementForItemNameAndPrice[i]);
            itemsAddedToCartPrice[i] = homePage.get().getItemPrice(itemElementForItemNameAndPrice[i]);
            homePage.get().addItemsToCart(itemElementsForAddToCart[i]);
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

    @Test(description = "Remove product items from the cart test")
    void removeProductItemsFromCartTest() {
        cartPage.get().verifyNumberOfItemsInCart(0)
                .clickContinueShoppingButton();
        homePage.get().addItemsToCart(3);
        cartPage.get().removeItemsFromCart(3);
    }

    @Test(description = "Verify navigation test ")
    void navigationTest() {
        cartPage.get().clickContinueShoppingButton();
        homePage.get().verifyProductsTextIsDisplayed()
                .clickItemName(0)
                .clickShoppingCartLink();
        cartPage.get().clickContinueShoppingButton();
        homePage.get().verifyProductsTextIsDisplayed();
    }

    @Test(description = "Verify social links")
    void socialLinkNavigationTest() {
        homeScreenTestCase.get().socialLinkNavigationTest();
    }
}
