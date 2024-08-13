package com.saucelab.provider;

import com.saucelab.constants.Constants;
import org.testng.annotations.DataProvider;

public class DataProviderUtils {
    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() {
        Object[][] data = {
                {"Negative", "username", "", ""},
                {"Negative", "password", "Test", ""},
                {"Negative","username", "", "Test"},
                {"Negative", "incorrect_user_pass","tandard_user", "secret_sauce"},
                {"Negative", "incorrect_user_pass","standard_user", "ecret_sauce"},
                {"Standard", "correct_user", Constants.STANDARD_USER, Constants.PASSWORD},
                {"Locked", "correct_user", Constants.LOCKED_OUT_USER, Constants.PASSWORD},
                {"Performance", "correct_user", Constants.PERFORMANCE_GLITCH_USER, Constants.PASSWORD},
                {"Error", "correct_user", Constants.ERROR_USER, Constants.PASSWORD},
                {"Visual", "correct_user", Constants.VISUAL_USER, Constants.PASSWORD}

        };
        return data;
    }

    @DataProvider(name = "checkOutInformation")
    public Object[][] getCheckoutInfo() {
        Object[][] data = {
                {"Negative", "Error: First Name is required", "", "", ""},
                {"Negative", "Error: First Name is required", "", "", "1234"},
                {"Negative", "Error: First Name is required", "", "lastName", ""},
                {"Negative", "Error: First Name is required", "", "lastName", "1234"},
                {"Negative", "Error: Last Name is required", "firstName", "", ""},
                {"Negative", "Error: Last Name is required", "firstName", "", "123"},
                {"Negative", "Error: Postal Code is required", "firstName", "lastName", ""},
                {"Positive", "", "firstName", "lastName", "1234"}
        };
        return data;
    }
}

