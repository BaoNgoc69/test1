package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    @FindBy(xpath = "//input[@id='user-name']")
    WebElement usernameInput;

    @FindBy(xpath = "//input[@id='password']")
    WebElement passwordInput;

    @FindBy(id = "login-button")
    WebElement loginButton;

    // Constructor - Khởi tạo Page Object
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Hành động nhập username
    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    // Hành động nhập password
    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    // Hành động bấm nút Login
    public void clickLogin() throws InterruptedException {
        loginButton.click();
        Thread.sleep(1000);
    }

    // Hành động đăng nhập gộp lại
    public void login(String username, String password) throws InterruptedException {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

}
