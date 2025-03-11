package crm.system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest{
    //WebDriver driver = new ChromeDriver();
     LoginPage loginPage;
     DashboardPage dashboardPage;
     CustomersPage customersPage;

    @BeforeClass
    public void setupTest() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginSuccess() {

        dashboardPage = loginPage.LoginFunc("admin@example.com", "123456");

        //Kiểm tra tiêu đề dashboard
        Assert.assertEquals(dashboardPage.getDashboardTitle(), "Dashboard");

        // Chuyển đến trang Customers
        customersPage = dashboardPage.clickMenuCustomer();
        Assert.assertEquals(customersPage.getCustomerTitle(), "Customers");

    }





}
