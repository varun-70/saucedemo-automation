package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
    public int numberOfItemsInCart() {
        return cartQuantity.size();
    }

    public float getItemPrices(int elementIndex) {
        String itemPriceInString = itemPrices.get(elementIndex).getText();
        return Float.parseFloat(itemPriceInString.substring(1));
    }

    /**
     * @param itemNameElementNumber to get the text
     */
    public String getItemName(int itemNameElementNumber) {
        return itemName.get(itemNameElementNumber).getText();
    }

    public void clickContinueShoppingButton() {
        continueShoppingButton.click();
    }

    public void clickRemoveButton(int elementIndex) {
        removeButton.get(elementIndex).click();
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
