package com.saucelab.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class CheckoutPage {

    // Declaration -----------------------------------------------
    @FindBy(id = "first-name")
    private WebElement firstNameTextField;

    @FindBy(id = "last-name")
    private WebElement lastNameTextField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeTextField;

    @FindBy(css = "span[class='title']")
    private WebElement pageTitle;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(xpath = "//button[@class='error-button']")
    private WebElement errorCloseIcon;

    @FindBy(css = "div[class='summary_value_label']")
    private WebElement paymentInformation;

    @FindBy(xpath = "(//div[@class='summary_value_label'])[2]")
    private WebElement shippingInformation;

    @FindBy(css = "div[class='summary_subtotal_label']")
    private WebElement subTotalPriceWithoutTax;

    @FindBy(css = "div[class='summary_tax_label']")
    private WebElement taxPrice;

    @FindBy(css = "div[class='summary_info_label summary_total_label']")
    private WebElement totalPrice;

    @FindBy(css = "div[class='inventory_item_price']")
    private List<WebElement> itemPrices;

    @FindBy(css = "img[class='pony_express']")
    public WebElement orderCompletedIcon;

    @FindBy(css = "h2[class='complete-header']")
    private WebElement orderCompletedHeader;

    @FindBy(css = "div[class='complete-text']")
    private WebElement orderCompletedText;

    // Utilization ---------------------------------------------

    @Step("Click cancel button")
    public CheckoutPage clickCancelButton() {
        cancelButton.click();
        return this;
    }

    @Step("Click continue button")
    public CheckoutPage clickContinueButton() {
        continueButton.click();
        return this;
    }

    @Step("Click finish button")
    public CheckoutPage clickFinishButton() {
        finishButton.click();
        return this;
    }

    @Step("Click back home button")
    public CheckoutPage clickBackHomeButton() {
        backHomeButton.click();
        return this;
    }

    @Step("Click error close icon")
    public CheckoutPage clickErrorCloseIcon() {
        errorCloseIcon.click();
        return this;
    }

    @Step("Click check out button")
    public CheckoutPage clickCheckoutButton() {
        checkoutButton.click();
        return this;
    }

    @Step("Enter first name")
    public CheckoutPage setFirstNameTextField(String firstName) {
        firstNameTextField.clear();
        firstNameTextField.sendKeys(firstName);
        return this;
    }

    @Step("Enter last name")
    public CheckoutPage setLastNameTextField(String lastName) {
        lastNameTextField.clear();
        lastNameTextField.sendKeys(lastName);
        return this;
    }

    @Step("Enter postal code")
    public CheckoutPage setPostalCodeTextField(String postalCode) {
        postalCodeTextField.clear();
        postalCodeTextField.sendKeys(postalCode);
        return this;
    }

    @Step("Verify error message")
    public CheckoutPage verifyErrorMessage(String expectedMessage) {
        Assert.assertEquals(errorMessage.getText(), expectedMessage);
        return this;
    }

    @Step("Verify order completed header")
    public CheckoutPage verifyOrderCompletedHeader(String expectedText) {
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

    @Step("Verify page title")
    public CheckoutPage assertPageTitle(pageTitles pageTitles) {
        Assert.assertEquals(pageTitle.getText(), pageTitles.getValue());
        return this;
    }

    @Step("Verify payment information")
    public CheckoutPage assertPaymentInformation() {
        Assert.assertEquals(paymentInformation.getText(), "SauceCard #31337");
        return this;
    }

    @Step("Verify shipping information")
    public CheckoutPage assertShippingInformation() {
        Assert.assertEquals(shippingInformation.getText(), "Free Pony Express Delivery!");
        return this;
    }

    @Step("Verify sub total price")
    public CheckoutPage assertSubTotalPrice() {
        double subTotalPriceExpected = getItemPrice();
        double subTotalPriceActual = Double.parseDouble(subTotalPriceWithoutTax.getText().substring(13));
        Assert.assertEquals(subTotalPriceActual, subTotalPriceExpected);
        return this;
    }

    @Step("Verify tax price")
    public CheckoutPage assertTaxPrice() {
        double itemPrice = getItemPrice();
        double taxPriceExpected = Math.round((itemPrice * 0.08) * 100.0) / 100.0;

        double taxPriceActual = Double.parseDouble(taxPrice.getText().substring(6));
        Assert.assertEquals(taxPriceActual, taxPriceExpected);
        return this;
    }

    @Step("Verify total price")
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
