package org.goodiemania.hecate.api;

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
    public String checkHealthCheck() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/health/", baseUri)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            String body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(body, String.class);
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<ListenerConfiguration> getAllListeners() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/", baseUri)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            String body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<ListenerConfiguration> getListener(final String listenerId) {
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/configuration/%s", baseUri, listenerId)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            String body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
            ListenerConfiguration listenerConfiguration = mapper.readValue(body, ListenerConfiguration.class);
            return Optional.ofNullable(listenerConfiguration);
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeListener(final ListenerConfiguration configuration) {
        try {
            String configurationString = mapper.writeValueAsString(configuration);

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("%s/configuration/%s", baseUri, configuration.getId())))
                    .method("PUT", HttpRequest.BodyPublishers.ofString(configurationString))
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteListener(final String listenerId) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("%s/configuration/%s", baseUri, listenerId)))
                    .method("DELETE", HttpRequest.BodyPublishers.noBody())
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void writeRule(final String configurationId, final Rule rule) {
        try {
            String ruleString = mapper.writeValueAsString(rule);

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("%s/configuration/%s/rules/%s", baseUri, configurationId, rule.getId())))
                    .method("PUT", HttpRequest.BodyPublishers.ofString(ruleString))
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteRule(final String configurationId, final String ruleId) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("%s/configuration/%s/rules/%s", baseUri, configurationId, ruleId)))
                    .method("DELETE", HttpRequest.BodyPublishers.noBody())
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Log> getAllLogs() {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/logs/", baseUri)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            String body = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Log> getAllLogsForListener(final String listenerId) {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/logs/%s", baseUri, listenerId)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            String body = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return mapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteLogsForListener(final String listenerId) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("%s/logs/%s", baseUri, listenerId)))
                    .method("DELETE", HttpRequest.BodyPublishers.noBody())
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
