package util;

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
                {"Standard", "correct_user","standard_user", "secret_sauce"},
                {"Locked", "correct_user","locked_out_user", "secret_sauce"},
                {"Performance", "correct_user","performance_glitch_user", "secret_sauce"},
                {"Error", "correct_user","error_user", "secret_sauce"},
                {"Visual", "correct_user","visual_user", "secret_sauce"}

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

