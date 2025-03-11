package common;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import java.nio.file.StandardCopyOption; // Import thêm nếu cần


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;


public class BaseTest {
    WebDriver driver;


    @BeforeMethod
    public void openBrowser() {
       // driver.get("https://crm.anhtester.com/admin/authentication");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    @Test
    public void Login() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Cập nhật path nếu cần
        driver.get("https://crm.anhtester.com/admin/authentication");
        // login
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("admin@example.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        if (!driver.findElement(By.xpath("//input[@id='remember']")).isSelected()) {
            driver.findElement(By.xpath("//input[@id='remember']")).click();
        }
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

        // chup man hinh sau khi login xong
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("screenshot.png");

        try {
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("admin@example.com");
//        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
//        if (!driver.findElement(By.xpath("//input[@id='remember']")).isSelected()) {
//            driver.findElement(By.xpath("//input[@id='remember']")).click();
//        }
//        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
//

    }



    @AfterMethod
    public void closeDriver() {
        driver.quit();

    }
}
