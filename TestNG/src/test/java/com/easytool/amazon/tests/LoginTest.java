package com.easytool.amazon.tests;

import com.aventstack.extentreports.ExtentTest;
import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.LoginPage;
import com.easytool.amazon.pages.WelcomePage;
import utils.ConfigReader;
import com.easytool.amazon.utils.ExtentTestManager;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Đăng nhập vào hệ thống")
    public void testLogin() throws InterruptedException {
        // Load file cấu hình
        ConfigReader.load("dev"); // Chỉ định môi trường cần load (ví dụ: "dev")

        ExtentTest test = ExtentTestManager.getTest();
        test.info("🔐 Bắt đầu đăng nhập vào hệ thống...");

        BaseTestHelper baseTest = new BaseTestHelper(driver);
        LoginPage loginPage = new LoginPage(driver, baseTest);
        WelcomePage welcomePage = new WelcomePage(driver, baseTest);

        // Lấy email và password từ file cấu hình
        String email = ConfigReader.get("email");
        String password = ConfigReader.get("password");

        if (email == null || password == null) {
            test.fail("❌ Không thể lấy thông tin đăng nhập từ file cấu hình!");
            Assert.fail("❌ Không thể lấy thông tin đăng nhập từ file cấu hình!");
            return;
        }

        loginPage.login(email, password);

        if (welcomePage.isLoginSuccessful()) {
            test.pass("✅ Đăng nhập thành công.");
        } else {
            test.fail("❌ Đăng nhập thất bại!");
            Assert.fail("❌ Đăng nhập thất bại!");
        }
    }
}
