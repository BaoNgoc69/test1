package com.easytool.amazon.tests;

import com.easytool.amazon.pages.BaseTestHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class BaseTest {
    protected static WebDriver driver;
    protected BaseTestHelper baseTestHelper;

    @BeforeSuite
    public void setup() {  // Chỉ chạy 1 lần trước tất cả test
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://admin.shopify.com/store/easy-tool-dev/apps/easyamazontool_development/welcome");
            System.out.println("✅ Trình duyệt đã mở.");


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
