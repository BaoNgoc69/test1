package herokuapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage {

    WebDriver driver;

    @FindBy(id = "username")
    WebElement InputUsername;

    @FindBy(id = "password")
    WebElement InputPassword;

    @FindBy(xpath = "//i[@class='fa fa-2x fa-sign-in']")
    WebElement ClickButtonLogin;

    @FindBy(xpath = "//div[@id='flash']")
    WebElement flashErrorMess;

    @FindBy(xpath = "//h4[contains(text(),'Welcome to the Secure Area. When you are done clic')]")
    WebElement messgeLoginSuccess;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void InputUsername(String username) {
        InputUsername.sendKeys(username);
    }

    public void InputPassword(String password) {
        InputPassword.sendKeys(password);
    }

    public void ClickButtonLogin() {
        ClickButtonLogin.click();
    }

    public boolean isSuccessMessageDisplayed() {
        return messgeLoginSuccess.getText().contains("Welcome to the Secure Area. When you are done click logout below.");
    }

    public boolean isErrorMessageDisplayed() {
        return flashErrorMess.getText().contains("Your username is invalid!") ||
                flashErrorMess.getText().contains("Your password is invalid!");
    }

    public void loginFunc(String username, String password) {
        InputUsername(username);
        InputPassword(password);
        ClickButtonLogin();

    }




}
