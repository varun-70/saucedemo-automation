package com.saucelab.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class CheckoutPage {

    // Declaration -----------------------------------------------
    @FindBy(id = "first-name")
    WebElement firstNameTextField;

    @FindBy(id = "last-name")
    WebElement lastNameTextField;

    @FindBy(id = "postal-code")
    WebElement postalCodeTextField;

    @FindBy(css = "span[class='title']")
    WebElement pageTitle;

    @FindBy(id = "cancel")
    WebElement cancelButton;

    @FindBy(id = "continue")
    WebElement continueButton;

    @FindBy(id = "finish")
    WebElement finishButton;

    @FindBy(id = "back-to-products")
    WebElement backHomeButton;

    @FindBy(id = "checkout")
    WebElement checkoutButton;

    @FindBy(css = "h3[data-test='error']")
    WebElement errorMessage;

    @FindBy(xpath = "//button[@class='error-button']")
    WebElement errorCloseIcon;

    @FindBy(css = "div[class='summary_value_label']")
    WebElement paymentInformation;

    @FindBy(xpath = "(//div[@class='summary_value_label'])[2]")
    WebElement shippingInformation;

    @FindBy(css = "div[class='summary_subtotal_label']")
    WebElement subTotalPriceWithoutTax;

    @FindBy(css = "div[class='summary_tax_label']")
    WebElement taxPrice;

    @FindBy(css = "div[class='summary_info_label summary_total_label']")
    WebElement totalPrice;

    @FindBy(css = "div[class='inventory_item_price']")
    List<WebElement> itemPrices;

    @FindBy(css = "img[class='pony_express']")
    public WebElement orderCompletedIcon;

    @FindBy(css = "h2[class='complete-header']")
    WebElement orderCompletedHeader;

    @FindBy(css = "div[class='complete-text']")
    WebElement orderCompletedText;

    // Utilization ---------------------------------------------

    public CheckoutPage clickCancelButton() {
        cancelButton.click();
        return this;
    }

    public CheckoutPage clickContinueButton() {
        continueButton.click();
        return this;
    }

    public CheckoutPage clickFinishButton() {
        finishButton.click();
        return this;
    }

    public CheckoutPage clickBackHomeButton() {
        backHomeButton.click();
        return this;
    }

    public CheckoutPage clickErrorCloseIcon() {
        errorCloseIcon.click();
        return this;
    }

    public CheckoutPage clickCheckoutButton() {
        checkoutButton.click();
        return this;
    }

    public CheckoutPage setFirstNameTextField(String firstName) {
        firstNameTextField.clear();
        firstNameTextField.sendKeys(firstName);
        return this;
    }

    public CheckoutPage setLastNameTextField(String lastName) {
        lastNameTextField.clear();
        lastNameTextField.sendKeys(lastName);
        return this;
    }

    public CheckoutPage setPostalCodeTextField(String postalCode) {
        postalCodeTextField.clear();
        postalCodeTextField.sendKeys(postalCode);
        return this;
    }

    public CheckoutPage verifyErrorMessage(String expectedMessage) {
        Assert.assertEquals(errorMessage.getText(), expectedMessage);
        return this;
    }

    public CheckoutPage getOrderCompletedHeader(String expectedText) {
        Assert.assertEquals(orderCompletedHeader.getText(), expectedText);
        return this;
    }

    public String getOrderCompletedText() {
        return orderCompletedText.getText();
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public boolean pageTitleDisplayed(pageTitles pageTitles) {
        if (pageTitle.getText().equals(pageTitles.getValue())) {
            return true;
        } else
            return false;
    }

    public CheckoutPage assertPageTitle(pageTitles pageTitles) {
        Assert.assertEquals(pageTitle.getText(), pageTitles.getValue());
        return this;
    }

    public CheckoutPage assertPaymentInformation() {
        Assert.assertEquals(paymentInformation.getText(), "SauceCard #31337");
        return this;
    }

    public CheckoutPage assertShippingInformation() {
        Assert.assertEquals(shippingInformation.getText(), "Free Pony Express Delivery!");
        return this;
    }

    public CheckoutPage assertSubTotalPrice() {
        double subTotalPriceExpected = getItemPrice();
        double subTotalPriceActual = Double.parseDouble(subTotalPriceWithoutTax.getText().substring(13));
        Assert.assertEquals(subTotalPriceActual, subTotalPriceExpected);
        return this;
    }

    public CheckoutPage assertTaxPrice() {
        double itemPrice = getItemPrice();
        double taxPriceExpected = Math.round((itemPrice * 0.08) * 100.0) / 100.0;

        double taxPriceActual = Double.parseDouble(taxPrice.getText().substring(6));
        Assert.assertEquals(taxPriceActual, taxPriceExpected);
        return this;
    }

    public CheckoutPage assertTotalPrice() {
        double itemPrice = getItemPrice();
        double taxRoundOff = Math.round((itemPrice * 0.08) * 100.0) / 100.0;
        double totalPriceExpected = itemPrice + taxRoundOff;

        double totalPriceActual = Double.parseDouble(totalPrice.getText().substring(8));
        Assert.assertEquals(totalPriceActual, totalPriceExpected);
        return this;
    }

    double getItemPrice() {
        double itemPrice = 0;
        for (int i=0; i<itemPrices.size(); i++) {
            String itemPriceInString = itemPrices.get(i).getText();
            itemPrice = itemPrice + Double.parseDouble(itemPriceInString.substring(1));
        }
        return itemPrice;
    }

    public enum pageTitles {
        checkoutYourInformation("Checkout: Your Information"),
        checkoutOverview("Checkout: Overview"),
        checkoutComplete("Checkout: Complete!"),
        yourCart("Your Cart"),
        products("Products");

        private final String value;

        pageTitles(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Initialization ------------
    public CheckoutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
