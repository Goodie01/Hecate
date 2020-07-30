package org.goodiemania.hecate.server.configuration.then;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import org.goodiemania.hecate.server.listener.RequestContext;

public class PassThrough extends Then {
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    private String newBaseUri;

    @Override
    public boolean process(final RequestContext context) {
        final URI uri = URI.create(newBaseUri + context.getRequest().getPath());
        final String httpMethod = context.getRequest().getHttpMethod();
        final String body = context.getRequest().getBody();
        final Map<String, String> headers = context.getRequest().getHeaders();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri)
                .method(httpMethod, HttpRequest.BodyPublishers.ofString(body));

        headers.forEach(builder::header);

        try {
            final HttpResponse<String> httpResponse = httpClient.send(builder.build(),
                    HttpResponse.BodyHandlers.ofString());

            context.getResponse().setHeaders(httpResponse.headers().map());
            context.getResponse().setBody(httpResponse.body());
            context.getResponse().setStatusCode(httpResponse.statusCode());
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Internal Hecate error");
        }

        return false;
    }

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public String getNewBaseUri() {
        return newBaseUri;
    }

    public void setNewBaseUri(final String newBaseUri) {
        this.newBaseUri = newBaseUri;
    }
}
