package org.goodiemania.hecate.managers.listeners;

import java.util.Map;

public class RequestContext {
    private RequestInfo request;
    private ResponseInfo response;

    public RequestContext() {
        //no-op for jackson
    }

    private RequestContext(final RequestInfo request, final ResponseInfo response) {
        this.request = request;
        this.response = response;
    }

    public static RequestContext of(final String path, final String httpMethod, final String body, final Map<String, String> headers) {
        return new RequestContext(RequestInfo.of(path, httpMethod, body, headers), new ResponseInfo());
    }

    public RequestInfo getRequest() {
        return request;
    }

    public ResponseInfo getResponse() {
        return response;
    }

    public void setRequest(final RequestInfo request) {
        this.request = request;
    }

    public void setResponse(final ResponseInfo response) {
        this.response = response;
    }
}
