package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static java.lang.Thread.sleep;

public class InventoryPage extends BasePage {

    By inventoryTabLocator = By.xpath("//ul[@class='tabs-container']//span[normalize-space()='Inventory']");
    By configLinkLocator = By.xpath("//a[normalize-space()='How to use Inventory tab?']");
    By btnClickAllToAmazon = By.xpath("//button[span[contains(text(), 'Send all to Amazon')]]");
    By btnClickDisableAllProduct = By.xpath("//button[contains(text(), 'Disable all products')]");
    By productNotFoundMessageLocator = By.xpath("//span[@class='not-found-sync-h' and contains(text(), 'Products not found')]");

    public InventoryPage(WebDriver driver, BaseTestHelper baseTestHelper) {
        super(driver, baseTestHelper);
        PageFactory.initElements(driver, this);
    }

    public boolean openInventoryTab() {
        try {
            switchToIframe();

            // ⚡ Dùng JavaScript để click tránh bị element khác che
            WebElement inventoryTab = wait.until(ExpectedConditions.presenceOfElementLocated(inventoryTabLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", inventoryTab);
            System.out.println("✅ Đã click vào tab 'Inventory' (bằng JS)");

            // ⬇ Scroll tới link hướng dẫn nếu nó nằm ngoài vùng nhìn thấy
            WebElement configLink = wait.until(ExpectedConditions.presenceOfElementLocated(configLinkLocator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", configLink);
            Thread.sleep(300); // chờ scroll hoàn tất

            // ✅ Kiểm tra hiển thị
            Assert.assertTrue(configLink.isDisplayed(), "❌ Link 'How to use Inventory tab?' không hiển thị!");
            System.out.println("✅ Link 'How to use Inventory tab?' đã xuất hiện!");

            baseTestHelper.takeScreenshot(driver, "Đã click vào tab Inventory");
            Thread.sleep(200);
            return true;

        } catch (Exception e) {
            Assert.fail("⚠️ Lỗi khi mở tab Inventory: " + e.getMessage());
            return false;
        } finally {
            switchToDefaultContent();
        }
    }





    public void clickSendAllToAmazon() {
        try {
            switchToIframe();

            WebElement sendAllButton = wait.until(ExpectedConditions.elementToBeClickable(btnClickAllToAmazon));
            sendAllButton.click();
            baseTestHelper.takeScreenshot(driver, "btnInventory");

            Assert.assertTrue(true, "✅ Đã click vào nút 'Send all to Amazon'");
        } catch (Exception e) {
            Assert.fail("⚠️ Lỗi khi click vào 'Send all to Amazon': " + e.getMessage());
        } finally {
            switchToDefaultContent();
        }
    }

    public void verifyUIOnNoProductsFound() {
        System.out.println("🧪 Đang kiểm tra UI khi không có sản phẩm nào được hiển thị...");
        try {
            switchToIframe(); // Đảm bảo bạn đã chuyển vào đúng iframe

            // 1. Kiểm tra thông báo "Products not found" hiển thị
            try {
                // Sử dụng biến 'wait' từ BasePage hoặc được khởi tạo trong constructor
                WebElement notFoundMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(productNotFoundMessageLocator));
                Assert.assertTrue(notFoundMessage.isDisplayed(), "❌ Thông báo 'Products not found' KHÔNG hiển thị.");
                System.out.println("✅ Thông báo 'Products not found' đã hiển thị.");
            } catch (TimeoutException e) {
                baseTestHelper.takeScreenshot(driver,"Loi_KhongThay_ProductNotFound");
                Assert.fail("❌ Không tìm thấy thông báo 'Products not found' trong thời gian chờ.");
            }

            // 2. Kiểm tra nút "Send all to Amazon" KHÔNG tồn tại/hiển thị
            List<WebElement> sendAllButtons = driver.findElements(btnClickAllToAmazon);
            Assert.assertTrue(sendAllButtons.isEmpty(), "❌ Nút 'Send all to Amazon' VẪN tồn tại khi không có sản phẩm.");
            System.out.println("✅ Nút 'Send all to Amazon' không tồn tại/hiển thị.");

            // 3. (Tùy chọn) Kiểm tra nút "Disable all products" cũng KHÔNG tồn tại/hiển thị
            List<WebElement> disableAllButtons = driver.findElements(btnClickDisableAllProduct);
            Assert.assertTrue(disableAllButtons.isEmpty(), "❌ Nút 'Disable all products' VẪN tồn tại khi không có sản phẩm.");
            System.out.println("✅ Nút 'Disable all products' không tồn tại/hiển thị.");

            // Chụp ảnh màn hình để xác nhận trạng thái UI
            baseTestHelper.takeScreenshot(driver, "XacNhan_UI_KhongCoSanPham");

        } catch (Exception e) {
            // Bắt các lỗi không mong muốn khác
            baseTestHelper.takeScreenshot(driver,"Loi_KiemTra_UI_KhongCoSanPham");
            Assert.fail("⚠️ Lỗi xảy ra trong quá trình kiểm tra UI khi không có sản phẩm: " + e.getMessage());
        } finally {
            switchToDefaultContent(); // Luôn đảm bảo chuyển về default content
        }
    }


    // Kiem tra xem có san pham nào hay ko
    public boolean isProductAvailable() {
        try {
            switchToIframe();  // Quan trọng: phải chuyển vào iframe trước khi kiểm tra

            // Nếu KHÔNG tìm thấy thông báo "Products not found" thì nghĩa là CÓ sản phẩm
            boolean isNotFoundVisible = !driver.findElements(productNotFoundMessageLocator).isEmpty();

            return !isNotFoundVisible;  // Có sản phẩm nếu KHÔNG có thông báo
        } finally {
            switchToDefaultContent();  // Luôn chuyển về default sau khi kiểm tra
        }
    }

//    public void clickDisableAllProducts() {
//        try {
//            switchToIframe();
//
//            WebElement disableAllBtn = wait.until(ExpectedConditions.elementToBeClickable(btnClickDisableAllProduct));
//            disableAllBtn.click();
//            baseTestHelper.takeScreenshot(driver, "Disable all product");
//
//            Assert.assertTrue(true, "✅ Đã click vào nút 'Disable all products'");
//        } catch (Exception e) {
//            Assert.fail("⚠️ Lỗi khi click 'Disable all products': " + e.getMessage());
//        } finally {
//            switchToDefaultContent();
//        }
//    }

}
