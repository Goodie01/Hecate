package org.goodiemania.hecate.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import org.goodiemania.hecate.confuration.ListenerConfiguration;
import org.goodiemania.hecate.confuration.Rule;
import org.goodiemania.hecate.logs.Log;

public class HecateApiImpl implements HecateApi {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final String baseUri;

    public HecateApiImpl(final HttpClient httpClient, final ObjectMapper mapper, final String baseUri) {
        this.httpClient = httpClient;
        this.mapper = mapper;
        this.baseUri = baseUri;
    }

    @Override
    public List<ListenerConfiguration> getAllListeners() {
        HttpRequest.Builder httpResponse = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/", baseUri)))
                .method("get", HttpRequest.BodyPublishers.noBody());

        try {
            String body = httpClient.send(httpResponse.build(), HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(body, new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<ListenerConfiguration> getListener(final String listenerId) {
        HttpRequest.Builder httpResponse = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/%s", baseUri, listenerId)))
                .method("get", HttpRequest.BodyPublishers.noBody());

        try {
            String body = httpClient.send(httpResponse.build(), HttpResponse.BodyHandlers.ofString()).body();
            ListenerConfiguration listenerConfiguration = mapper.readValue(body, ListenerConfiguration.class);
            return Optional.ofNullable(listenerConfiguration);
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeListener(final ListenerConfiguration configuration) {
        String configurationString = null;
        try {
            configurationString = mapper.writeValueAsString(configuration);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }

        HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/%s", baseUri, configuration.getId())))
                .method("put", HttpRequest.BodyPublishers.ofString(configurationString));
    }

    @Override
    public void deleteListener(final String listenerId) {
        HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/%s", baseUri, listenerId)))
                .method("delete", HttpRequest.BodyPublishers.noBody());
    }

    @Override
    public void writeRules(final String configurationId, final Rule rule) {
        String ruleString = null;
        try {
            ruleString = mapper.writeValueAsString(rule);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }

        HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/%s/rules/%s", baseUri, configurationId, rule.getId())))
                .method("put", HttpRequest.BodyPublishers.ofString(ruleString));
    }

    @Override
    public void deleteRule(final String configurationId, final String ruleId) {
        HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/%s/rules/%s", baseUri, configurationId, ruleId)))
                .method("delete", HttpRequest.BodyPublishers.noBody());
    }

    @Override
    public List<Log> getAllLogs() {
        HttpRequest.Builder httpResponse = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/logs/", baseUri)))
                .method("get", HttpRequest.BodyPublishers.noBody());

        try {
            String body = httpClient.send(httpResponse.build(), HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(body, new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Log> getAllLogsForListener(final String listenerId) {
        HttpRequest.Builder httpResponse = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/logs/%s", baseUri, listenerId)))
                .method("get", HttpRequest.BodyPublishers.noBody());

        try {
            String body = httpClient.send(httpResponse.build(), HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(body, new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteLogsForListener(final String listenerId) {
        HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/logs/%s", baseUri, listenerId)))
                .method("delete", HttpRequest.BodyPublishers.noBody());
    }
}
