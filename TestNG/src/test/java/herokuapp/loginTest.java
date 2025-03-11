package herokuapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class loginTest extends BaseTest {
    LoginPage loginPage;

    @Test(priority = 1)
    public void loginSuccess() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(driver);
        loginPage.loginFunc("tomsmith", "SuperSecretPassword!");
        Thread.sleep(2000);
        Assert.assertTrue(loginPage.isSuccessMessageDisplayed(), "Login thành công nhưng không hiển thị thông báo!");

    }

    @Test(priority = 2)
    public void loginFail() {
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(driver);
        loginPage.loginFunc("tomsmith123", "SuperSecretPassword!");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Login thất bại nhưng không hiển thị thông báo lỗi!");
    }


}
