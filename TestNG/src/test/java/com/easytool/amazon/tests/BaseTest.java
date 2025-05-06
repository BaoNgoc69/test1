package com.easytool.amazon.tests;

import com.easytool.amazon.pages.BaseTestHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;

public class BaseTest {
    protected static WebDriver driver;
    protected BaseTestHelper baseTestHelper;
    protected static String env;

    @BeforeSuite
    public void setup() {
        if (driver == null) {
            // 👇 Lấy biến môi trường truyền vào, mặc định là dev nếu không truyền
            env = System.getProperty("environment", "prod");

            // ✅ Load file config tương ứng từ thư mục config
            ConfigReader.load(env);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("force-device-scale-factor=0.9");
            options.addArguments("high-dpi-support=0.9");

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();

            // 👉 Lấy URL từ file config đã load
            String baseUrl = ConfigReader.get("baseUrl");
            driver.get(baseUrl);

            System.out.println("✅ Môi trường đang chạy: " + env);
        }
    }

    @AfterSuite
    public void teardown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 Đã đóng Chrome.");
        }
    }
}
