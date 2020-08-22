package org.goodiemania.hecate.confuration.then;

import org.goodiemania.hecate.managers.listeners.RequestContext;

public class StaticReturn extends Then {
    private String staticResponse;
    private int responseCode;

    @Override
    public boolean process(final RequestContext context) {
        context.getResponse().setBody(staticResponse);
        context.getResponse().setStatusCode(responseCode);

        return false;
    }

    public String getStaticResponse() {
        return staticResponse;
    }

    public void setStaticResponse(final String staticResponse) {
        this.staticResponse = staticResponse;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(final int responseCode) {
        this.responseCode = responseCode;
    }
}
