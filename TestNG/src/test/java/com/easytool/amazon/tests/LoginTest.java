package com.easytool.amazon.tests;

import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.LoginPage;
import com.easytool.amazon.pages.WelcomePage;

import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseTest{

    @Test(priority = 1, description = "Đăng nhập vào hệ thống")
    public void testLogin() {
        BaseTestHelper baseTest = new BaseTestHelper(driver);
        LoginPage loginPage = new LoginPage(driver, baseTest);

        WelcomePage welcomePage = new WelcomePage(driver, baseTest);

        loginPage.login("tester@inter-soft.com", "guKH4&D*bn$ETZ&");
        Assert.assertTrue(welcomePage.isLoginSuccessful(), "❌ Đăng nhập thất bại!");
    }

}
