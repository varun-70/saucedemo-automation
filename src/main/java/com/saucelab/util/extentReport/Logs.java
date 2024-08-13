package com.saucelab.util.extentReport;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.saucelab.util.Listener;

import java.io.File;
import java.util.Base64;

/**
 * To log into the html file
 */
public class Logs {
    /**
     * Log the passed string to the HTML reports
     *
     * @param logMessage The message to log
     */
    public static void log(String logMessage) {
        System.out.println(logMessage);
        Listener.node.log(Status.INFO, logMessage);
    }

    /**
     * Logs the warning message passed in string to the HTML reports
     *
     * @param logMessage The message to log
     */
    public static void logWarning(String logMessage) {
        System.out.println("WARNING: " + logMessage);
        Listener.test.log(Status.WARNING, logMessage);
    }

    /**
     * Logs the failed message passed in string to the HTML reports
     *
     * @param logMessage The message to log
     */
    public static void logFail(String logMessage) {
        System.out.println("Fail: " + logMessage);
        Listener.test.log(Status.FAIL, logMessage);
    }

    /**
     * Logs the label with the given status, label and colour
     *
     * @param status in pass/fail/warning
     * @param label  label name in String
     * @param color  of the label to be added
     */
    public static void logLabel(Status status, String label, ExtentColor color) {
        Listener.test.log(status, MarkupHelper.createLabel(label, color));
    }

    /**
     * Logs image into the html file
     *
     * @param status    in pass/fail/warning
     * @param details   to log a message along with image
     * @param imageFile to log in html file
     */
    public static void logImage(Status status, String details, File imageFile) {
        String encodedImageInString = ExtentReportTestNG.encodeFileToBase64Binary(imageFile);
        Listener.node.log(status, details, MediaEntityBuilder.createScreenCaptureFromBase64String(encodedImageInString).build());
    }

    /**
     * Logs image into html file
     *
     * @param status    in pass/fail/warning
     * @param details   to log a message along with image
     * @param imageFile in decoded byte[] format of the image file
     */
    public static void logImage(Status status, String details, byte[] imageFile) {
        String encodedImageInString = Base64.getEncoder().encodeToString(imageFile);
        Listener.node.log(status, details, MediaEntityBuilder.createScreenCaptureFromBase64String(encodedImageInString).build());
    }
}
