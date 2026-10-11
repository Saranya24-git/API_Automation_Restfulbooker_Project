package reporting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance(
            String reportPath, String projectName) {
    	
    	//ensures that the manager initializes the shared report only once during a normal TestNG execution
        if (extent == null) {
        	
        	// Create report directory            
            try {
                Files.createDirectories(
                    Paths.get(reportPath).toAbsolutePath().getParent()
                );
            } catch (IOException e) {
                throw new RuntimeException(
                    "Unable to create report directory", e
                );
            }
            // Configure ExtentSparkReporter
            ExtentSparkReporter spark =
                new ExtentSparkReporter(reportPath);
            
            // Create ExtentReports
            spark.config().setDocumentTitle(projectName);
            spark.config().setReportName(projectName);

            // Attach the reporter
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add project information
            extent.setSystemInfo("Project", projectName);
            extent.setSystemInfo("Framework", "Java + TestNG");
        }

        return extent;
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}