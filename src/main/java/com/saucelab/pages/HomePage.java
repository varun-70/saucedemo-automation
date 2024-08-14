package com.saucelab.pages;

import com.saucelab.util.HelperUtil;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    WebDriver driver;
    HelperUtil helperUtil;

    // Declaration -----------------------------------------------
    @FindBy(css = "a[class^='shopping']")
    public WebElement linkShoppingCart;

    @FindBy(css = "select[class='product_sort_container']")
    WebElement sortingDropDown;

    @FindBy(css = "div[class='app_logo']")
    WebElement homeHeader;

    @FindBy(css = "button[id^='add-to-cart']")
    public List<WebElement> addToCartButton;

    @FindBy(css = "button[id^='remove']")
    public List<WebElement> removeButton;

    @FindBy(xpath = "//a[.='Twitter']")
    public WebElement twitterSocialLink;

    @FindBy(xpath = "//a[.='Facebook']")
    public WebElement facebookSocialLink;

    @FindBy(xpath = "//a[.='LinkedIn']")
    public WebElement linkedInSocialLink;

    @FindBy(css = "div[class='footer_copy']")
    WebElement copyRightLabel;

    @FindBy(css = "h3[data-test='error']")
    WebElement wrongNavigationError;

    @FindBy(css = "div[class='inventory_item_price']")
    List<WebElement> itemPrice;

    @FindBy(css = "div[class='inventory_item_name ']")
    List<WebElement> itemName;

    @FindBy(css = "div[class='cart_quantity']")
    public List<WebElement> cartQuantity;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(xpath = "//span[.='Products']")
    public WebElement productsText;

    // Utilization ---------------------------------------------
    public HomePage clickShoppingCartLink() {
        linkShoppingCart.click();
        return this;
    }

    public boolean isHomePageDisplayed() {
        try {
            return homeHeader.getText().equals("Swag Labs");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public HomePage addItemsToCart(int numberOfItemsToAddToCart) {
        for(int i = 0; i < numberOfItemsToAddToCart; i++) {
            addToCartButton.get(0).click();
        }
        return this;
    }

    public HomePage clickRemoveButton(int removeElementNumber) {
        removeButton.get(removeElementNumber - 1).click();
        return this;
    }

    public HomePage assertWrongNavigationError() {
        Assert.assertEquals(wrongNavigationError.getText(), "Epic sadface: You can only access '/inventory.html' when you are logged in.");
        return this;
    }

    public HomePage assertCopyRightLabel() {
        Assert.assertEquals(copyRightLabel.getText(), "© 2023 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy");
        return this;
    }

    public float getItemPrice(int i) {
        String priceInString = itemPrice.get(i).getText();
        return Float.parseFloat(priceInString.substring(1));
    }

    public HomePage asserSortByPrice(sorting sortBy) {
        double[] itemPrices = new double[itemPrice.size()];
        for (int i = 0; i < itemPrice.size(); i++) {
            itemPrices[i] = getItemPrice(i);
        }

        for (int i = 0; i < itemPrices.length - 1; i++) {
            if (itemPrices[i] > itemPrices[i + 1] && sortBy.equals(sorting.Price_low_to_high)) {
                Assert.assertTrue(false);
                return this;
            }
            if (itemPrices[i] < (itemPrices[i + 1]) && sortBy.equals(sorting.Price_high_to_low)) {
                Assert.assertTrue(false);
                return this;
            }
        }
        Assert.assertTrue(true);
        return this;
    }

    /**
     * @param itemNameElementNumber to get the text
     */
    public String getItemName(int itemNameElementNumber) {
        return itemName.get(itemNameElementNumber).getText();
    }

    public HomePage clickItemName(int itemNameElementNumber) {
        itemName.get(itemNameElementNumber).click();
        return this;
    }

    public HomePage assertSortByItemName(sorting sortBy) {
        String[] itemNames = new String[itemName.size()];
        for (int i = 0; i < itemName.size(); i++) {
            itemNames[i] = itemName.get(i).getText();
        }

        for (int i = 0; i < itemNames.length - 1; i++) {
            if (itemNames[i].compareTo(itemNames[i + 1]) > 0 && sortBy == sorting.Name_A_to_Z) {
                Assert.assertTrue(false);
                return this;
            }
            if (itemNames[i].compareTo(itemNames[i + 1]) < 0 && sortBy == sorting.Name_Z_to_A) {
                Assert.assertTrue(false);
                return this;
            }
        }
        Assert.assertTrue(true);
        return this;
    }

    public HomePage setSortingDropDown(sorting sortBy) {
        Select select = new Select(sortingDropDown);
        select.selectByValue(sortBy.getValue());
        return this;
    }

    public enum sorting {
        Name_A_to_Z("az"),
        Name_Z_to_A("za"),
        Price_low_to_high("lohi"),
        Price_high_to_low("hilo");

        private final String value;

        sorting(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public HomePage clickContinueShoppingButton() {
        continueShoppingButton.click();
        return this;
    }

    public HomePage clickTwitterSocialLink() {
        twitterSocialLink.click();
        return this;
    }

    public HomePage clickFacebookSocialLink() {
        facebookSocialLink.click();
        return this;
    }

    public HomePage clickLinkedInSocialLink() {
        linkedInSocialLink.click();
        return this;
    }

    public String getProductSectionText() {
        return productsText.getText();
    }

    public HomePage verifySocialLinkNavigationTest(String expectedLink) {
        List<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        helperUtil.wait(Duration.ofSeconds(2));

        Assert.assertEquals(driver.getCurrentUrl(), expectedLink);
        driver.close();
        driver.switchTo().window(browserTabs.get(0));
        return this;
    }

    // Initialization ------------
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        helperUtil = new HelperUtil(driver);
    }
}
