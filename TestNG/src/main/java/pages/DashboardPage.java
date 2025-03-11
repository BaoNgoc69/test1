package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.dnd.DragGestureEvent;

public class DashboardPage {
    WebDriver driver;

    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    WebElement click_menu;

    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    WebElement click_log_out;

    // contractor
    public DashboardPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // action
    public void click_menu(){
        click_menu.click();
    }

    public void click_log_out(){
        click_log_out.click();
    }

    // action gop lai
    public void log_out() throws InterruptedException {
        click_menu.click();
        Thread.sleep(2000);
        click_log_out.click();
    }


}
