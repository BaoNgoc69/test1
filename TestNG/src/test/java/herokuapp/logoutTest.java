package herokuapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class logoutTest extends BaseTest{

    LoginPage loginPage;
    LogOutPage logoutPage;

    @Test
    public void login(){
        loginPage = new LoginPage(driver);
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage.loginFunc("tomsmith", "SuperSecretPassword!");

    }
    @Test
    public void logout() throws InterruptedException {
        
        logoutPage = new LogOutPage(driver);
        logoutPage.logout();
        Thread.sleep(2000);
        Assert.assertTrue(logoutPage.checkLogout(), "Logout failed");
    }


}
