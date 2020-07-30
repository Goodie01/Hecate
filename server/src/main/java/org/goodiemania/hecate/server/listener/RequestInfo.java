package org.goodiemania.hecate.server.listener;

import io.javalin.http.Context;
import java.util.Map;

public class RequestInfo {
    final String path;
    final String httpMethod;
    final String body;
    final Map<String, String> headers;

    private RequestInfo(final String path, final String httpMethod, final String body, final Map<String, String> headers) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.body = body;
        this.headers = headers;
    }

    public static RequestInfo of(final Context context) {
        final String path = context.path();
        final String httpMethod = context.method();
        final String body = context.body();
        final Map<String, String> headers = context.headerMap();

        return new RequestInfo(path, httpMethod, body, headers);
    }

    public String getPath() {
        return path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
