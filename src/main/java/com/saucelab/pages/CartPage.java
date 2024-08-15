package com.saucelab.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class CartPage {

    // Declaration -----------------------------------------------
    @FindBy(css = "div[class='cart_quantity']")
    private List<WebElement> cartQuantity;

    @FindBy(css = "div[class='inventory_item_price']")
    private List<WebElement> itemPrices;

    @FindBy(css = "div[class='inventory_item_name']")
    private List<WebElement> itemName;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//button[.='Remove']")
    public List<WebElement> removeButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(xpath = "//span[.='Your Cart']")
    private WebElement yourCartText;

    // Utilization ---------------------------------------------
    @Step("Verify number of items in cart")
    public CartPage verifyNumberOfItemsInCart(int expectedResult) {
        Assert.assertEquals(cartQuantity.size(), expectedResult);
        return this;
    }

    @Step("Get item prices")
    public float getItemPrices(int elementIndex) {
        String itemPriceInString = itemPrices.get(elementIndex).getText();
        return Float.parseFloat(itemPriceInString.substring(1));
    }

    @Step("Remove items from cart")
    public CartPage removeItemsFromCart(int numberOfItemsToRemoveToCart) {
        for (int i = numberOfItemsToRemoveToCart-1; i >= 0; i--) {
            removeButton.get(i).click();
        }
        return this;
    }

    /**
     * @param itemNameElementNumber to get the text
     */
    @Step("Get item name")
    public String getItemName(int itemNameElementNumber) {
        return itemName.get(itemNameElementNumber).getText();
    }

    @Step("Click continue shopping button")
    public CartPage clickContinueShoppingButton() {
        continueShoppingButton.click();
        return this;
    }

    @Step("Click remove button")
    public CartPage clickRemoveButton(int elementIndex) {
        removeButton.get(elementIndex).click();
        return this;
    }

    public boolean isYourCartDisplayed() {
        try {
            return yourCartText.getText().equals("Your Cart");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Verify your cart is displayed")
    public CartPage verifyYourCartIsDisplayed() {
        Assert.assertEquals(yourCartText.getText(), "Your Cart");
        return this;
    }

    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    // Initialization ------------
    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
