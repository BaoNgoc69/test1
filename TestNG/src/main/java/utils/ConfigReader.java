package utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    public static void load(String env) {
        String fileName = "config/config-" + env + ".properties";
        try {
            properties.load(ConfigReader.class.getClassLoader().getResourceAsStream(fileName));
            System.out.println("✅ Đã load config từ: " + fileName);
        } catch (IOException | NullPointerException e) {
            System.err.println("⚠ Không thể load file config: " + fileName);
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
