package com.easytool.amazon.tests;

import com.aventstack.extentreports.ExtentReports;
import com.easytool.amazon.pages.BaseTestHelper;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

public class BaseTest {
    protected static WebDriver driver;
    protected BaseTestHelper baseTestHelper;


    @BeforeSuite
    public void setup() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();

            // 👇 Zoom 80%
            options.addArguments("force-device-scale-factor=0.9");
            options.addArguments("high-dpi-support=0.9");

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.get("https://admin.shopify.com/store/easy-tool-dev/apps/easyamazontool_development/welcome");

            System.out.println("✅ Trình duyệt đã mở full + zoom 50%");
        }
    }

    @AfterSuite
    public void teardown() {  // Chạy 1 lần sau tất cả test
        if (driver != null) {
            driver.quit();
            System.out.println("Đã đóng Chrome.");
        }
    }
}
