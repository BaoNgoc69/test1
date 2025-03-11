package crm.system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
WebDriver driver;

    @BeforeClass
    public void init(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://crm.anhtester.com/admin/authentication");
    }

    @AfterClass
    public void close(){
        driver.quit();
    }

}
