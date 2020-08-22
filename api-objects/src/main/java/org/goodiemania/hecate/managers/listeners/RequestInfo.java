package org.goodiemania.hecate.managers.listeners;

import java.util.Map;

public class RequestInfo {
    private String path;
    private String httpMethod;
    private String body;
    private Map<String, String> headers;

    public RequestInfo() {
        //no-op for jackson
    }

    private RequestInfo(final String path, final String httpMethod, final String body, final Map<String, String> headers) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.body = body;
        this.headers = headers;
    }


    public static RequestInfo of(final String path, final String httpMethod, final String body, final Map<String, String> headers) {
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

    public void setPath(final String path) {
        this.path = path;
    }

    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }
}
