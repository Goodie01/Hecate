package org.goodiemania.hecate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.goodiemania.hecate.configuration.ConfigurationFile;
import org.goodiemania.hecate.configuration.ConfigurationProvider;

public class Main {
    public static void main(String[] args) {
        final String props = System.getProperty("props");

        if (StringUtils.isEmpty(props)) {
            System.err.println("props system property empty");
            System.exit(1);
        }

        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();

        ConfigurationProvider configuration = new ConfigurationFile(props, objectMapper);

        new MetaContext(objectMapper, configuration).reStart();
    }
}
