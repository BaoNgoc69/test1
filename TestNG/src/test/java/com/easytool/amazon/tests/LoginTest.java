package com.easytool.amazon.tests;

import com.aventstack.extentreports.ExtentTest;
import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.LoginPage;
import com.easytool.amazon.pages.WelcomePage;
import com.easytool.amazon.utils.ExtentTestManager;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Đăng nhập vào hệ thống")
    public void testLogin() throws InterruptedException {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("🔐 Bắt đầu đăng nhập vào hệ thống...");

        BaseTestHelper baseTest = new BaseTestHelper(driver);
        LoginPage loginPage = new LoginPage(driver, baseTest);
        WelcomePage welcomePage = new WelcomePage(driver, baseTest);

        loginPage.login("tester@inter-soft.com", "guKH4&D*bn$ETZ&");

        if (welcomePage.isLoginSuccessful()) {
            test.pass("✅ Đăng nhập thành công.");
        } else {
            test.fail("❌ Đăng nhập thất bại!");
            Assert.fail("❌ Đăng nhập thất bại!");
        }
    }
}
