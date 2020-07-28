package org.goodiemania.hecate.server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public enum Configuration {
    MANAGEMENT_PORT;

    public String get() {
        String propertyFileName = System.getProperty("props", "default.properties");

        Properties properties = new Properties();
        try (FileReader propertiesFileReader = new FileReader(propertyFileName)) {
            properties.load(propertiesFileReader);
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("Unable to load properties file", e);
        }
        return (String) properties.get(this.toString());
    }
}
