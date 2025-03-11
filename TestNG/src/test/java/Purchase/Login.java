package Purchase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class Login {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void init() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void login() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        String url_Actual = driver.getCurrentUrl();
        String expected_url = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(url_Actual, expected_url, "Login success");
    }

    @Test  (dependsOnMethods = "login")
    public void add_to_cart() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Thread.sleep(2000);
        // Click "Add to Cart" button
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();

        // Click on Cart icon
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();

        // Verify product in cart
        Thread.sleep(2000);
        String productName = driver.findElement(By.className("inventory_item_name")).getText();
        Assert.assertEquals(productName, "Sauce Labs Backpack", "Product was not added to cart!");

        System.out.println("✅ Add to cart test passed!");
    }

    @Test (dependsOnMethods = "add_to_cart")
    public void checkOut() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@id='checkout']")).click();
        driver.findElement(By.xpath("//input[@id='first-name']")).sendKeys("bao ngoc");
        driver.findElement(By.xpath("//input[@id='last-name']")).sendKeys("phan");
        driver.findElement(By.xpath("//input[@id='postal-code']")).sendKeys("7000");
        Thread.sleep(2000);
        driver.findElement(By.xpath(" //input[@id='continue']")).click();
        String product_name = driver.findElement(By.xpath("//div[normalize-space()='Shipping Information:']")).getText();

        Assert.assertEquals(product_name,"Shipping Information:", "Checkout success");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@id='finish']")).click();
        String successful_completion = driver.findElement(By.xpath("//h2[normalize-space()='Thank you for your order!']")).getText();
        Assert.assertEquals(successful_completion, "Thank you for your order!","Thanh toan that bai");


    }


    @Test  (dependsOnMethods = "checkOut")
    public void logout() throws InterruptedException {
        driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
        driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
        String url_After_logout = "https://www.saucedemo.com/";
        Assert.assertEquals(url_After_logout, "https://www.saucedemo.com/", "Logout success");

    }



    @AfterClass
    public void close() {
        driver.quit();
    }

}
