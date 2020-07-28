package org.goodiemania.hecate.server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public enum Props {
    MANAGEMENT_PORT;

    public String get() {
        String propertyFileName = System.getProperty("props", "default.properties");

        java.util.Properties properties = new java.util.Properties();
        try (FileReader propertiesFileReader = new FileReader(propertyFileName)) {
            properties.load(propertiesFileReader);
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("Unable to load properties file", e);
        }
        return (String) properties.get(this.toString());
    }
}
