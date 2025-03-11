package crm.system;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "email")
    WebElement inputEmail;

    @FindBy(id = "password")
    WebElement inputPassword;

    @FindBy(xpath = "//label[normalize-space()='Remember me']")
    WebElement clickRememberMe;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    WebElement clickLogin;

    public void enterEmail(String email){
        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email);

    }

    public void enterPassword(String password){
        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys(password);

    }

    public void ClickButtonRememberMe(){
        wait.until(ExpectedConditions.elementToBeClickable(clickRememberMe)).click();
    }

    public void ClickButtonlogin(){
        wait.until(ExpectedConditions.elementToBeClickable(clickLogin)).click();
    }

    public DashboardPage LoginFunc(String email, String password)  {
        enterEmail(email);
        enterPassword(password);
        ClickButtonRememberMe();
        ClickButtonlogin();
        return new DashboardPage(driver);
    }

}

