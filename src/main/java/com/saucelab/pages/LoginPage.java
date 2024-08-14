package com.saucelab.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


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

    public LoginPage verifyHeaderText(String expectedResult) {
        Assert.assertEquals(headertext.getText(), expectedResult);
        return this;
    }

    public LoginPage setUsernametextfield(String username) {
        usernametextfield.sendKeys(username);
        return this;
    }

    public LoginPage setPasswordtextfield(String password) {
        passwordtextfield.sendKeys(password);
        return this;
    }

    public LoginPage tapLoginButton() {
        loginbutton.click();
        return this;
    }

    public String getError() {
        return error.getText();
    }


}