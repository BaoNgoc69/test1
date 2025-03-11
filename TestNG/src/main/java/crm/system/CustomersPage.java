package crm.system;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomersPage {
    WebDriver driver;

    public CustomersPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[normalize-space()='New Customer']")
    WebElement clickNewCustomer;

    @FindBy(xpath = "//input[@id='company']")
    WebElement inputCustomerName;

    @FindBy(xpath = "//button[@class='btn btn-primary only-save customer-form-submiter']")
    WebElement clickSaveCustomer;

    public void clickbtnAddCustomer(){
        clickNewCustomer.click();
    }

    public void enterCustomerName(String customerName){
        inputCustomerName.sendKeys(customerName);
    }

    public void clickBtnSaveCustomer(){
        clickSaveCustomer.click();
    }

    public void createCustomer(String customerName){
        clickbtnAddCustomer();
        enterCustomerName(customerName);
        clickBtnSaveCustomer();
    }

    // **Thêm hàm kiểm tra khách hàng đã được thêm**
    public boolean isCustomerAdded(String customerName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement addedCustomer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//td[contains(text(), '" + customerName + "')]")
            ));
            return addedCustomer.isDisplayed();
        } catch (Exception e) {
            return false; // Nếu hết thời gian mà vẫn không thấy khách hàng thì trả về false
        }
    }

    public String getCustomerTitle() {
        return driver.getTitle(); // Lấy tiêu đề trang để kiểm tra
    }
}
