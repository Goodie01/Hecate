package org.goodiemania.hecate.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.goodiemania.hecate.MetaContext;
import org.goodiemania.hecate.configuration.MockConfiguration;
import org.junit.jupiter.api.Test;

class ExampleUsage {
    @Test
    void doThing() {
        final MockConfiguration mockConfiguration = new MockConfiguration(null);

        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();

        final MetaContext metaContext = new MetaContext(objectMapper, mockConfiguration);
    }
}
