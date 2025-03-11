package crm.system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {
    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[normalize-space()='Customers']")
    WebElement menuCustomers;

    public void clickMenu(){
        menuCustomers.click();
    }

    public String getDashboardTitle() {
        return driver.getTitle(); // Lấy tiêu đề trang để kiểm tra
    }

    public CustomersPage clickMenuCustomer(){
        clickMenu();
        return new CustomersPage(driver);

    }


}
