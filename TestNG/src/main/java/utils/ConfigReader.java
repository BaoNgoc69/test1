package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    public static void load(String env) {
        String fileName = "config/config-" + env + ".properties";
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                System.err.println("⚠ Không thể tìm thấy file cấu hình: " + fileName);
                return;
            }
            properties.load(inputStream);
            System.out.println("✅ Đã load config từ: " + fileName);
        } catch (IOException e) {
            System.err.println("⚠ Lỗi khi load file cấu hình: " + fileName);
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("⚠ Không tìm thấy key: " + key);
        }
        return value;
    }
}
