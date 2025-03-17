package com.easytool.amazon.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class BaseTestHelper {
    WebDriver driver;

    public BaseTestHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void deleteOldScreenshots(int maxFiles) {
        File screenshotDir = new File("screenshots/");
        if (!screenshotDir.exists() || !screenshotDir.isDirectory()) return;

        File[] files = screenshotDir.listFiles((dir, name) -> name.endsWith(".png"));
        if (files == null || files.length <= maxFiles) return;

        // Sắp xếp theo thời gian tạo file (cũ nhất trước)
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));

        // Xóa file cũ nhất nếu vượt quá giới hạn
        for (int i = 0; i < files.length - maxFiles; i++) {
            files[i].delete();
            System.out.println("🗑️ Deleted old screenshot: " + files[i].getName());
        }
    }

    public void takeScreenshot(WebDriver driver, String testName) {
        deleteOldScreenshots(3); // Giữ lại tối đa 10 ảnh
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = "screenshots/" + timestamp + "_" + testName+ ".png";

        File srcFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
            System.out.println("📸 Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scrollDown(int px) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String cmd = "window.scrollBy(0, " + px + ");";
        js.executeScript(cmd);
        Thread.sleep(500); // Đợi trang tải
    }
}
