package page;

import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends BaseClass {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]")
    private WebElement headertext;

    @FindBy(xpath = "//*[@id=\"user-name\"]")
    private WebElement usernametextfield;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordtextfield;

    @FindBy(xpath = "//*[@id=\"login-button\"]")
    private WebElement loginbutton;

    public String getHeaderText() {
        return headertext.getText();
    }

    public void setUsernametextfield(String username) {
        usernametextfield.sendKeys(username);
    }

    public void setPasswordtextfield(String password) {
        passwordtextfield.sendKeys(password);
    }

    public void tapLoginButton() {
        loginbutton.click();
    }

}