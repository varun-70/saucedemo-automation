package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]")
    private WebElement headertext;

    @FindBy(id = "user-name")
    private WebElement usernametextfield;

    @FindBy(id = "password")
    private WebElement passwordtextfield;

    @FindBy(id = "login-button")
    private WebElement loginbutton;

    @FindBy(xpath = "//*[@id='login_button_container']/div/form/div[3]/h3")
    private WebElement error;

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

    public String getError() {
        return error.getText();
    }


}