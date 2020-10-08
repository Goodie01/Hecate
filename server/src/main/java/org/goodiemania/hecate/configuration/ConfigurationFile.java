package org.goodiemania.hecate.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationFile {
    private String propsLocation;
    private final ObjectMapper objectMapper;

    public ConfigurationFile(final String propsLocation, final ObjectMapper objectMapper) {
        this.propsLocation = propsLocation;
        this.objectMapper = objectMapper;
    }

    public Configuration get() {
        try (final FileReader fileReader = new FileReader(this.propsLocation)) {
            return objectMapper.readValue(fileReader, Configuration.class);
        } catch (IOException e) {
            System.err.println("Failed to load configuration");
            throw new IllegalStateException(e);
        }
    }

    public void update(final Configuration configuration) {
        try (final FileWriter fileWriter = new FileWriter(this.propsLocation)) {
            objectMapper.writeValue(fileWriter, configuration);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read config");
        }

    }
}
