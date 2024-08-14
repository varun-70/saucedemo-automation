package com.saucelab.pages;

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
    public List<WebElement> cartQuantity;

    @FindBy(css = "div[class='inventory_item_price']")
    public List<WebElement> itemPrices;

    @FindBy(css = "div[class='inventory_item_name']")
    List<WebElement> itemName;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(xpath = "//button[.='Remove']")
    public List<WebElement> removeButton;

    @FindBy(id = "checkout")
    WebElement checkoutButton;

    @FindBy(xpath = "//span[.='Your Cart']")
    public WebElement yourCartText;

    // Utilization ---------------------------------------------
    public CartPage verifyNumberOfItemsInCart(int expectedResult) {
        Assert.assertEquals(cartQuantity.size(), expectedResult);
        return this;
    }

    public float getItemPrices(int elementIndex) {
        String itemPriceInString = itemPrices.get(elementIndex).getText();
        return Float.parseFloat(itemPriceInString.substring(1));
    }

    public CartPage removeItemsFromCart(int numberOfItemsToRemoveToCart) {
        for (int i = numberOfItemsToRemoveToCart-1; i >= 0; i--) {
            removeButton.get(i).click();
        }
        return this;
    }

    /**
     * @param itemNameElementNumber to get the text
     */
    public String getItemName(int itemNameElementNumber) {
        return itemName.get(itemNameElementNumber).getText();
    }

    public CartPage clickContinueShoppingButton() {
        continueShoppingButton.click();
        return this;
    }

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

    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    // Initialization ------------
    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
