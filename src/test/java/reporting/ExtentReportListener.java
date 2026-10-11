package reporting;

import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

//Connect TestNG's execution lifecycle to ExtentReports
public class ExtentReportListener
        implements ITestListener, IExecutionListener {

    private ExtentReports extent;

    @Override
    public void onExecutionStart() {

        extent = ExtentManager.getInstance(
            "reports/api-extent-report.html",
            "Restful Booker API Automation"
        );
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName =
            result.getTestClass().getRealClass().getSimpleName()
            + " :: "
            + result.getMethod().getMethodName();

        ExtentTest test = extent.createTest(testName);

        ExtentTestManager.setTest(test);

        test.info("Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        recordAndClear(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        recordAndClear(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        recordAndClear(result);
    }

    private void recordAndClear(ITestResult result) {
        ExtentTestManager.recordResult(result);
        ExtentTestManager.clear();
    }

    @Override
    public void onExecutionFinish() {
        ExtentManager.flush();
    }
}