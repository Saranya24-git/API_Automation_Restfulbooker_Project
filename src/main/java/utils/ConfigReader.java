package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        String environment = System.getProperty("env", "qa").toLowerCase();
        
        if (!environment.equals("qa") &&
        	    !environment.equals("prod")) {

        	    throw new IllegalArgumentException(
        	            "Unsupported environment: " + environment);
        	}

        String fileName = "config-" + environment + ".properties";

        try (InputStream input =
                     ConfigReader.class.getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException(
                        "Configuration file not found: " + fileName);
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to load configuration file: " + fileName, e);
        }
    }

    public static String get(String key) {

        String value = properties.getProperty(key);

        if (value == null) {
            throw new RuntimeException(
                    "Configuration key not found: " + key);
        }

        return value;
    }
}