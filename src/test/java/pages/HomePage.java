package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class HomePage {

    // Declaration -----------------------------------------------
    @FindBy(css = "a[class^='shopping']")
    WebElement linkShoppingCart;

    @FindBy(css = "select[class='product_sort_container']")
    WebElement sortingDropDown;

    @FindBy(css = "div[class='app_logo']")
    WebElement homeHeader;

    @FindBy(css = "button[id^='add-to-cart']")
    public List<WebElement> addToCartButton;

    @FindBy(css = "button[id^='remove']")
    public List<WebElement> removeButton;

    @FindBy(xpath = "//a[.='Twitter']")
    WebElement twitterSocialLink;

    @FindBy(xpath = "//a[.='Facebook']")
    WebElement facebookSocialLink;

    @FindBy(xpath = "//a[.='LinkedIn']")
    WebElement linkedInSocialLink;

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


    // Utilization ---------------------------------------------
    public void clickShoppingCartLink() {
        linkShoppingCart.click();
    }

    public boolean isHomePageDisplayed() {
        try {
            return homeHeader.getText().equals("Swag Labs");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /** @param addToCartElementNumber to click on it  */
    public void clickAddToCartButton(int addToCartElementNumber) {
        addToCartButton.get(addToCartElementNumber-1).click();
    }

    public void clickRemoveButton(int removeElementNumber) {
        removeButton.get(removeElementNumber-1).click();
    }

    public void assertWrongNavigationError() {
        Assert.assertEquals(wrongNavigationError.getText(),"Epic sadface: You can only access '/inventory.html' when you are logged in.");
    }

    public void assertCopyRightLabel() {
        Assert.assertEquals(copyRightLabel.getText(), "© 2023 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy");
    }

    public double getItemPrice(int i) {
        String priceInString = itemPrice.get(i).getText();
        String[] priceInArray = priceInString.split("\\$");
        return Double.parseDouble(priceInArray[1]);
    }

    public boolean asserSortByPrice(sorting sortBy) {
        double[] itemPrices = new double[itemPrice.size()];
        for (int i=0; i<itemPrice.size(); i++) {
            itemPrices[i] = getItemPrice(i);
        }

        for (int i = 0; i < itemPrices.length - 1; i++) {
            if (itemPrices[i] > itemPrices[i + 1] && sortBy.equals(sorting.Price_low_to_high)) {
                return false;
            }
            if (itemPrices[i] < (itemPrices[i + 1]) && sortBy.equals(sorting.Price_high_to_low)) {
                return false;
            }
        }
        return true;
    }

    /** @param itemNameElementNumber to get the text */
    public String getItemName(int itemNameElementNumber) {
        return itemPrice.get(itemNameElementNumber).getText();
    }

    public boolean assertSortByItemName(sorting sortBy) {
        String[] itemNames = new String[itemName.size()];
        for (int i=0; i<itemName.size(); i++) {
            itemNames[i] = itemName.get(i).getText();
        }

        for (int i = 0; i < itemNames.length - 1; i++) {
            if (itemNames[i].compareTo(itemNames[i + 1]) > 0 && sortBy==sorting.Name_A_to_Z) {
                return false;
            }
            if (itemNames[i].compareTo(itemNames[i + 1]) < 0 && sortBy==sorting.Name_Z_to_A) {
                return false;
            }
        }
        return true;
    }

    public void setSortingDropDown(sorting sortBy) {
        Select select = new Select(sortingDropDown);
        select.selectByValue(sortBy.getValue());
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

    public void clickContinueShoppingButton() {
        continueShoppingButton.click();
    }

    public void clickTwitterSocialLink() {
        twitterSocialLink.click();
    }

    public void clickFacebookSocialLink() {
        facebookSocialLink.click();
    }

    public void clickLinkedInSocialLink() {
        linkedInSocialLink.click();
    }

    // Initialization ------------
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
