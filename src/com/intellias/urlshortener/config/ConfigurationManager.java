package com.intellias.urlshortener.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigurationManager {
    private static final String PROPERTIES_FILE = "application.properties";
    private static ConfigurationManager instance;
    private final Properties properties;

    private ConfigurationManager() {
        properties = loadProperties();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("Properties file not found: " + PROPERTIES_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
        return props;
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

    public String getPropertyValue(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getBase62Chars() {
        return properties.getProperty("url.shortener.base62.chars");
    }

    public int getBaseRadix() {
        return Integer.parseInt(properties.getProperty("url.shortener.base.radix", "62"));
    }

    public long getInitialCounter() {
        return Long.parseLong(properties.getProperty("url.shortener.initial.counter", "1"));
    }

    public String getBaseUrl() {
        return properties.getProperty("url.shortener.base.url");
    }
}
