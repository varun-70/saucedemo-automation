package util;

import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import util.extentReport.Logs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.testng.Assert.fail;

public class ImageComparison {
    AShot ashot;
    WebDriver driver;
    ImageDiffer imageDiffer;
    boolean captureBaseLineImage;

    /**
     * Compares 2 image using Ashot and fail the test if the comparison fails
     * @param fileName in String, to save actual image and diff image with the name
     * @param element in WebElement, to take screenshot
     */
    public void imageComparisonAshot(String fileName, WebElement element) {
        File actualImageFile = element.getScreenshotAs(OutputType.FILE);
        BufferedImage expectedImage = null;
        BufferedImage actualImage = null;
        try {
            FileUtils.copyFile(actualImageFile, new File(System.getProperty("user.dir") +
                    "/src/test/resources/imageComparison/actualImage/" + fileName + ".png"));
            if(!captureBaseLineImage) {
                expectedImage = ImageIO.read(new File(System.getProperty("user.dir") +
                        "/src/test/resources/imageComparison/expectedImage/" + fileName + ".png"));
                actualImage = ImageIO.read(new File(System.getProperty("user.dir") +
                        "/src/test/resources/imageComparison/actualImage/" + fileName + ".png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!captureBaseLineImage) {
            ImageDiff diff = imageDiffer.makeDiff(expectedImage, actualImage);

            if (diff.hasDiff()) {
                try {
                    ImageIO.write(diff.getMarkedImage(),"PNG",new File(System.getProperty("user.dir") +
                            "/src/test/resources/imageComparison/differenceImage/" + fileName + "_diff.png"));
                    fail();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (AssertionError e) {
                    Logs.logImage(Status.FAIL, "Visual Assertion failed", new File(System.getProperty("user.dir") +
                            "/src/test/resources/imageComparison/differenceImage/" + fileName + "_diff.png"));
                    fail("Visual Assertion, Image did not pass the visual assertion");
                }
            } else if (!diff.hasDiff()) {
                Logs.log("Image comparison passed");
            }
        }
    }

    /** Names for the images used for image comparison */
    public enum imageName {
        cart_icon_without_items_in_cart,
        cart_icon_with_one_item,
        twitter_social_icon,
        facebook_social_icon,
        linkedin_social_icon,
        checkout_complete_icon
    }

    /** To set the captureBaseLine flag to capture the images without performing the image comparison */
    public void setCaptureBaseLineImage(boolean captureBaseLineImage) {
        this.captureBaseLineImage = captureBaseLineImage;
    }

    public ImageComparison(WebDriver driver) {
        this.driver = driver;
        ashot = new AShot();
        imageDiffer = new ImageDiffer();
    }
}
