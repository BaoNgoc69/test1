package crm.system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CustomersTest extends BaseTest{

    LoginPage loginPage;
    CustomersPage customersPage;
    DashboardPage dashboardPage;

    @BeforeClass
    public void setupTest() {

        // Login vào hệ thống
        loginPage = new LoginPage(driver);
        dashboardPage = loginPage.LoginFunc("admin@example.com", "123456");

        // Kiểm tra login thành công
        Assert.assertEquals(dashboardPage.getDashboardTitle(), "Dashboard");

        // Chuyển sang trang Customers
        customersPage = dashboardPage.clickMenuCustomer();
        Assert.assertEquals(customersPage.getCustomerTitle(), "Customers");
    }
    @Test
    public void testAddNewCustomer() {
        // Thêm một khách hàng mới
        String customerName = "bao ngoc";
        customersPage.createCustomer(customerName);

        // Kiểm tra khách hàng được thêm thành công
        Assert.assertTrue(customersPage.isCustomerAdded(customerName), "Customer not found in list!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Đóng trình duyệt sau khi chạy xong test
        }
    }




}
