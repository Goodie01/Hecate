package org.goodiemania.hecate.server.listener;

import java.time.LocalDateTime;
import org.goodiemania.hecate.server.configuration.ListenerConfiguration;

public class Log {
    public final String listenerName;
    private final RequestInfo request;
    private final ResponseInfo response;
    private final long timeTaken;
    private final LocalDateTime time;

    private Log(final String listenerName, final RequestInfo request, final ResponseInfo response, final long timeTaken, final LocalDateTime time) {
        this.listenerName = listenerName;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
        this.time = time;
    }

    public static Log of(final ListenerConfiguration config, final RequestContext requestContext, final long timeTaken) {
        return new Log(config.getName(), requestContext.getRequest(), requestContext.getResponse(), timeTaken, LocalDateTime.now());
    }

    public String getListenerName() {
        return listenerName;
    }

    public RequestInfo getRequest() {
        return request;
    }

    public ResponseInfo getResponse() {
        return response;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
