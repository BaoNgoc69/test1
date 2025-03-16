
package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.time.Duration;

import static java.lang.Thread.sleep;

public class ConnectPage {
    WebDriver driver;
    WebDriverWait wait;
    BaseTestPage baseTestPage;
    String originalURL;
    By iframeLocator = By.xpath("//iframe[@title='[DEV] Amazon Easy Tool']");
    By connectTabLocator = By.xpath("//ul[@class='tabs-container']//span[@class='Polaris-Text--root Polaris-Text--bodySm Polaris-Text--medium'][normalize-space()='Connect']");
    By configLinkLocator = By.xpath("//a[normalize-space()='How to configure your Connect?']");
    By checkConnectButton = By.xpath("//button[.//span[text()='Check']]");
    By successMessage = By.xpath("//span[contains(@class, 'Polaris-Text--bodyMd Polaris-Text--success') and text()='Successfully connected to Amazon']");
    By addConnect = By.xpath("//span[normalize-space()='Get Started']");
    By textAddConnect = By.xpath("//p[contains(text(),'Grow your sales by expanding your business worldwi')]");
    By inputNameMkp = By.xpath("//div[@class='Polaris-TextField']//input");
    By selectRegion = By.xpath("//select[@id=':r6:']");
    By selectMKP = By.xpath("//select[@id=':rc:']");
    By selectOrder = By.xpath("//select[@id=':r8:']");
    By btnSaveAuthorize = By.xpath("//button[span[text()='Save & Authorize']]");
    By checkboxConfirmAuthorize = By.xpath("//div[@role='checkbox']");
    By btnConfirmAuthorize = By.xpath("//button[@class='primary' and text()='Confirm']");
    By inputEmail = By.xpath("//input[@type='email' and @id='ap_email' and @name='email']");
    By inputPassword = By.xpath("//input[@id='ap_password']");

    public ConnectPage(WebDriver driver, BaseTestPage baseTestPage) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.baseTestPage = baseTestPage; // ✅ Nhận từ test, không tự tạo mới
        PageFactory.initElements(driver, this);
    }


    public boolean openConnectTab() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);

            WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(connectTabLocator));
            connectButton.click();
            System.out.println("✅ Đã click vào tab 'Connect'");

            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(configLinkLocator));
            System.out.println("✅ Link 'How to configure your Connect?' đã xuất hiện!");
            sleep(200);
            return configLink.isDisplayed();
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi mở tab Connect: " + e.getMessage());
            return false;
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    public boolean switchToIframe(By iframeLocator) {
        try {
            WebElement iframeElement = new WebDriverWait(driver, Duration.ofSeconds(1))
                    .until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);
            System.out.println("✅ Đã chuyển vào iframe!");
            return true;
        } catch (NoSuchFrameException | TimeoutException e) {
            System.out.println("⚠ Không tìm thấy iframe: " + e.getMessage());
            return false;
        }
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void addConnect() throws InterruptedException {

        switchToIframe(iframeLocator);
        WebElement clickAddConnect = driver.findElement(addConnect);
        clickAddConnect.click();
        sleep(500);

        // input name
        WebElement inputName = driver.findElement(inputNameMkp);
        inputName.sendKeys("Amazon USA");
        baseTestPage.scrollDown(200);

        // select region
        WebElement dropdownRegion = driver.findElement(selectRegion);
        Select dropdown1 = new Select(dropdownRegion);
        dropdown1.selectByIndex(1);

        // select market place
        WebElement dropdownMKP = driver.findElement(selectMKP);
        Select dropdown2 = new Select(dropdownMKP);
        dropdown2.selectByIndex(1);

        // import order
        WebElement dropdownOrder = driver.findElement(selectOrder);
        Select dropdown3 = new Select(dropdownOrder);
        dropdown3.selectByIndex(1);

        sleep(200);
        baseTestPage.takeScreenshot("add mkp");
        WebElement clickSave= driver.findElement(btnSaveAuthorize);
        this.originalURL = driver.getCurrentUrl();
        System.out.println("URL hiện tại của trang connect"+originalURL);
        baseTestPage.scrollDown(200);
        sleep(1000);
        clickSave.click();
        System.out.println("Click Save Successfully");
        sleep(5000);
        authorizeConnection();
        sleep(1000);
    }

    public void checkAllConnections() {
        boolean ischeck = false;
        try {
            // 1️⃣ Chuyển vào iframe (nếu có)
            switchToIframe(iframeLocator);

            // 2️⃣ Lấy danh sách tất cả các nút "Check"
            List<WebElement> checkButtons = driver.findElements(By.xpath("//button[span[text()='Check']]"));

            // 3️⃣ Lặp qua từng nút và click

            for (WebElement checkButton : checkButtons) {
                ischeck = true;
                checkButton.click();

                System.out.println("✅ Đã click vào nút Check");
                baseTestPage.scrollDown(200);
                sleep(3000);

                // 4️⃣ Chờ thông báo kết nối thành công
                WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@class, 'Polaris-Text--success')]")
                ));
                System.out.println("✅ Kết nối thành công: " + successMessage.getText());
                // 5️⃣ Chờ một chút trước khi tiếp tục (tránh lỗi)

                sleep(1000);

            }
            baseTestPage.scrollDown(200);
            baseTestPage.takeScreenshot("Check connect");
            if (!ischeck) {
                this.addConnect();
                baseTestPage.takeScreenshot("Add connect");
            }


        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra kết nối: " + e.getMessage());
        } finally {
            // 6️⃣ Quay lại trang chính (nếu có dùng iframe)
            switchToDefaultContent();
        }
    }

    public void authorizeConnection() throws InterruptedException {

//        WebElement clickSave= driver.findElement(btnSaveAuthorize);
//        clickSave.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String url = driver.getCurrentUrl();
        wait.until(ExpectedConditions.urlContains(url));
        System.out.println("URL hiện tại: " + url);
        System.out.println("Page changed, locating email input...");
        driver.switchTo().defaultContent();
        WebElement inputName = driver.findElement(inputEmail);
        inputName.sendKeys("nhu@inter-soft.com" + Keys.ENTER);
        WebElement inputPass = driver.findElement(inputPassword);
        inputPass.sendKeys("Fed@#SoftToole" + Keys.ENTER);
        System.out.println("entered password");

        // Chờ nút "Sign In" có thể được click
        WebDriverWait waitClick = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement signInButton = waitClick.until(ExpectedConditions.elementToBeClickable(By.id("auth-signin-button")));
        System.out.println("Entered OTP");

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(this.originalURL)));
        System.out.println("✅ Trang đã thay đổi!");
        sleep(25000);

        By checkboxConfirmAuthorize = By.xpath("//div[@role='checkbox']");
        WebElement checkButtonConfirm = wait.until(ExpectedConditions.elementToBeClickable(checkboxConfirmAuthorize));
        checkButtonConfirm.click();
        System.out.println("✅ Đã nhấn nút Confirm");

        WebElement clickConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(btnConfirmAuthorize));
        clickConfirmButton.click();
        System.out.println("✅ Đã nhấn nút Confirm");

        // quay lại trang chính
        System.out.println("✅ Đã quay lại trang chính!");
        sleep(10000);

        // check
        try {
            // 1️⃣ Chuyển vào iframe (nếu có)
            switchToIframe(iframeLocator);

            // 2️⃣ Lấy danh sách tất cả các nút "Check"
            List<WebElement> checkButtons = driver.findElements(By.xpath("//button[span[text()='Check']]"));

            // 3️⃣ Lặp qua từng nút và click

            for (WebElement checkButton : checkButtons) {
                checkButton.click();

                System.out.println("✅ Đã click vào nút Check");
                baseTestPage.scrollDown(200);
                sleep(3000);

                // 4️⃣ Chờ thông báo kết nối thành công
                WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@class, 'Polaris-Text--success')]")
                ));
                System.out.println("✅ Kết nối thành công: " + successMessage.getText());
                // 5️⃣ Chờ một chút trước khi tiếp tục (tránh lỗi)

                sleep(1000);

            }
        }
        catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra kết nối: " + e.getMessage());
        }
        finally {
            // 6️⃣ Quay lại trang chính (nếu có dùng iframe)
            switchToDefaultContent();
        }
    }

}