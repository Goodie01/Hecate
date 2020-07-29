package org.goodiemania.hecate.server.configuration.then;

import io.javalin.http.Context;

public class StaticReturn extends Then {
    private String staticResponse;
    private int responseCode;

    @Override
    public boolean doTheThing(final Context context) {
        context.result(staticResponse);
        context.status(responseCode);

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
