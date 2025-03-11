package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        // Khởi tạo Page Object
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @Test
    public void testLogin() throws InterruptedException {
        // Đăng nhập vào hệ thống
        loginPage.login("standard_user", "secret_sauce");

        // Kiểm tra URL sau khi login thành công
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed!");
    }


    @Test
    public void testLogout() throws InterruptedException {
        loginPage.login("standard_user", "secret_sauce");
        Thread.sleep(2000);
        dashboardPage.log_out();
        // Kiểm tra đã quay lại trang login chưa
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
    }


    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
