package com.easytool.amazon.utils;
import org.testng.ITestListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportExample implements ITestListener {
    private static ExtentReports extentReports;
    private static ExtentTest test;

    public void onStart(ITestContext context) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    public void onTestStart(ITestResult result) {
        test = extentReports.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.pass("✅ Test Passed: " + result.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult result) {
        test.fail("❌ Test Failed: " + result.getMethod().getMethodName());
        test.fail(result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        test.skip("⚠ Test Skipped: " + result.getMethod().getMethodName());
    }

    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
