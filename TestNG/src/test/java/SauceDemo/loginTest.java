package SauceDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import sauceDemo.login_page;

import java.sql.Driver;

public class loginTest {
    login_page login;
    WebDriver driver;

    // khoi tao
    @BeforeMethod
    public void init(){
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        login = new login_page(driver);
    }


    @Test (priority = 2)
    public void login_success() throws InterruptedException {
        login.login("standard_user", "secret_sauce");
        Assert.assertTrue(login.isLoginSuccess(), "Login  thành công!");

    }

    @Test (priority = 1)
    public void login_fail() throws InterruptedException {
        login.login("standard_user", "secret_sauce1111");
        Assert.assertTrue(login.isLoginFail(), "Login không thành công!");

    }


    @AfterMethod
    public void close(){
        driver.quit();
    }

}
