package com.ivan.wallet.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The PropertiesUtil class is a utility class for loading and accessing properties from a configuration file.
 */
@UtilityClass
public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    /**
     * Load the properties from the configuration file.
     *
     * @throws RuntimeException If an error occurs while loading the properties.
     */
    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the value of a property based on the provided key.
     *
     * @param key The key of the property.
     * @return The value of the property.
     */
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}