package sauceDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class login_page {
    WebDriver driver;

    public login_page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='user-name']")
    WebElement input_userName;

    @FindBy(xpath = "//input[@id='password']")
    WebElement input_password;

    @FindBy(id = "login-button")
    WebElement click_buttonLogin;

    @FindBy(xpath = "//div[@class='app_logo']")
    WebElement home_logo;


    // ❌ Thông báo lỗi nếu login thất bại
    @FindBy(xpath = "//h3[@data-test='error']")
    WebElement error_message;

    public void enterUsername(String username) {
        input_userName.sendKeys(username);
    }

    public void enter_password(String password) {
        input_password.sendKeys(password);
    }

    public void click_button() {
        click_buttonLogin.click();
    }

    public void login(String username, String password) throws InterruptedException {
        enterUsername(username);
        enter_password(password);
        click_button();
        Thread.sleep(2000);
    }

    public boolean isLoginSuccess() {
        return home_logo.isDisplayed();
    }

    public boolean isLoginFail() {
        return error_message.isDisplayed();
    }


}