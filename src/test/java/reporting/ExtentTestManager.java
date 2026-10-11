package reporting;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestManager {

	//API project currently runs sequentially, ThreadLocal design to introduce parallel test execution in the future.
    private static final ThreadLocal<ExtentTest> TEST =
        new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static void setTest(ExtentTest test) {
        TEST.set(test);
    }

    public static ExtentTest getTest() {
        return TEST.get();
    }

    public static void recordResult(ITestResult result) {

        ExtentTest test = TEST.get();

        if (test == null) {
            return;
        }

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
        	//HTML report display the failure details and stack trace
            if (result.getThrowable() != null) {
                test.fail(result.getThrowable());
            } else {
                test.fail("Test failed");
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            if (result.getThrowable() != null) {
                test.skip(result.getThrowable());
            } else {
                test.skip("Test skipped");
            }
        }
    }

    public static void clear() {
    	//Removes the current test entry from the thread-local storage once the result has been recorded
        TEST.remove();
    }
}