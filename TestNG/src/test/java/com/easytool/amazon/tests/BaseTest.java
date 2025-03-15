package com.easytool.amazon.tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected static WebDriver driver;  // Dùng chung WebDriver

    @BeforeSuite
    public void setup() {  // Chỉ chạy 1 lần trước tất cả test
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://admin.shopify.com/store/easy-tool-prod/apps/easyamazontool/welcome");
            System.out.println("✅ Trình duyệt đã mở.");
        }
    }

    @AfterSuite
    public void teardown() {  // Chạy 1 lần sau tất cả test
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("✅ Trình duyệt đã đóng.");
        }
    }


}
