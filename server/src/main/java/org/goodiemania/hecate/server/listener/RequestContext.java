package org.goodiemania.hecate.server.listener;

import io.javalin.http.Context;

public class RequestContext {
    private final RequestInfo request;
    private final ResponseInfo response;

    private RequestContext(final RequestInfo request, final ResponseInfo response) {
        this.request = request;
        this.response = response;
    }

    public static RequestContext of(final Context context) {
        return new RequestContext(RequestInfo.of(context), new ResponseInfo());
    }

    public RequestInfo getRequest() {
        return request;
    }

    public ResponseInfo getResponse() {
        return response;
    }
}
