package SauceDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sauceDemo.ProductPage;
import sauceDemo.login_page;

public class ProductTests {
    login_page login;
    WebDriver driver;
    ProductPage productPage;

    @BeforeMethod
    public void init(){
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        login = new login_page(driver);
        productPage = new ProductPage(driver);
    }

    @Test
    public void testProductListIsDisplayed() throws InterruptedException {
        login.login("standard_user", "secret_sauce");
        Thread.sleep(2000);
        System.out.println("url hien tại"+driver.getCurrentUrl());
        // Kiểm tra có ít nhất 1 sản phẩm hiển thị trên trang Dashboard
        Assert.assertTrue(productPage.isProductListDisplayed(), "Danh sách sản phẩm không hiển thị!");


    }

    @AfterMethod
    public void close() {
        driver.quit();
    }


}
