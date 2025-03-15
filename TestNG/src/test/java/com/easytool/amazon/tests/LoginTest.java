package com.easytool.amazon.tests;

import com.easytool.amazon.pages.BaseTestPage;
import com.easytool.amazon.pages.ConnectPage;
import com.easytool.amazon.pages.LoginPage;
import com.easytool.amazon.pages.WelcomePage;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseTest{

    @Test(priority = 1, description = "Đăng nhập vào hệ thống")
    public void testLogin() {
        BaseTestPage baseTest = new BaseTestPage(driver);
        LoginPage loginPage = new LoginPage(driver, baseTest);

        WelcomePage welcomePage = new WelcomePage(driver, baseTest);

        loginPage.login("tester@inter-soft.com", "guKH4&D*bn$ETZ&");
        Assert.assertTrue(welcomePage.isLoginSuccessful(), "❌ Đăng nhập thất bại!");
    }

}
