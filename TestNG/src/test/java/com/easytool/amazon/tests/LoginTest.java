package com.easytool.amazon.tests;

import com.easytool.amazon.pages.ConnectPage;
import com.easytool.amazon.pages.LoginPage;
import com.easytool.amazon.pages.WelcomePage;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;
    WelcomePage welcomePage;
    ConnectPage connectPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://admin.shopify.com/store/easy-tool-prod/apps/easyamazontool/welcome" );

        loginPage = new LoginPage(driver);
        welcomePage = new WelcomePage(driver);
        connectPage = new ConnectPage(driver);
    }

    @Test(priority = 1, description = "Đăng nhập vào hệ thống" )
    public void testLogin() {
        loginPage.login("tester@inter-soft.com", "guKH4&D*bn$ETZ&" );
        Assert.assertTrue(welcomePage.isLoginSuccessful(), "❌ Đăng nhập thất bại!" );
    }

    @Test(priority = 2, dependsOnMethods = "testLogin", description = "Mở tab Connect và kiểm tra kết nối")
    public void testOpenConnectTab() {
        ConnectPage connectPage = new ConnectPage(driver);

        // Mở tab Connect và kiểm tra xem có mở thành công không
        boolean isConnectTabOpened = connectPage.openConnectTab();
        Assert.assertTrue(isConnectTabOpened, "Không tìm thấy link cấu hình Connect!");

        // Click vào Check Connect và chờ thông báo thành công
        connectPage.checkAllConnections();
    }



    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
