package org.goodiemania.hecate.server.listener;

import java.util.List;
import java.util.Map;

public class ResponseInfo {
    private Map<String, List<String>> headers;
    private String body;
    private int statusCode;

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }
}
