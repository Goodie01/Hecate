package org.goodiemania.hecate.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.http.HttpClient;
import java.util.Objects;

public class HecateApiBuilder {
    private HttpClient httpClient;
    private ObjectMapper mapper;
    private String baseUri;

    public HecateApiBuilder setHttpClient(final HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public HecateApiBuilder setMapper(final ObjectMapper mapper) {
        this.mapper = mapper;
        return this;
    }

    public HecateApiBuilder setBaseUri(final String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public HecateApi build() {
        Objects.requireNonNull(baseUri, "You must supply the URI of your Hecate instance");

        if (mapper == null) {
            mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .addModule(new Jdk8Module())
                    .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .build();
        }

        if (httpClient == null) {
            httpClient = HttpClient.newBuilder().build();
        }

        return new HecateApiImpl(httpClient, mapper, baseUri);
    }
}