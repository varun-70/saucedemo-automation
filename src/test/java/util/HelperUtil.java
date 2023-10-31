package util;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class HelperUtil {
    WebDriver driver;

    /**
     * Used to handle exception using try catch for the wait statement
     * @param duration in Duration
     */
    public void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /** Helper methods for turning off the implicit wait by changing wait time to 0 */
    public void turnOffImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    /** Helper methods for turning on the implicit wait by changing wait time to given parameter */
    public void turnOnImplicitWaits(Duration duration) {
        driver.manage().timeouts().implicitlyWait(duration);
    }

    public HelperUtil(WebDriver driver) {
        this.driver = driver;
    }
}
