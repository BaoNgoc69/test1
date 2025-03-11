package herokuapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LogOutPage {

    WebDriver driver;

    public LogOutPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this );
    }

    @FindBy(xpath = "//i[@class='icon-2x icon-signout']")
    WebElement buttonSignout;

    @FindBy(xpath = "//div[@id='flash']")
    WebElement messLogoutsuccess;

    public void logout(){
        buttonSignout.click();
    }

    public void ClickButtonLogout(){
        logout();
    }
    public boolean checkLogout(){
        return messLogoutsuccess.getText().contains("You logged out of the secure area!");
    }





}
