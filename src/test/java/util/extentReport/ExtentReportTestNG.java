package util.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.testng.IReporter;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * To generate extent report, Need to specify the location for result.html to save, Report name, Document title,
 * and system info like Tester and name
 */
public class ExtentReportTestNG implements IReporter {
    static ExtentReports extent;

    /**
     * To config the document file
     * @return ExtentReports object
     */
    public static ExtentReports getReporterObject() {
        String path = System.getProperty("user.dir") + "/reports/report.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("csc-Automation");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Varun");

        return extent;
    }

    /**
     * To encode the file using Base64 and return in string
     *
     * @param file that needs to be encoded
     * @return encodedFile in String
     */
    public static String encodeFileToBase64Binary(File file) {
        String encodedFile = null;
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            encodedFile = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedFile;
    }

}
