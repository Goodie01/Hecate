package org.goodiemania.hecate.server.listener;

import java.time.LocalDateTime;
import org.goodiemania.hecate.server.configuration.ListenerConfiguration;

public class Log {
    public final String listenerId;
    private final RequestInfo request;
    private final ResponseInfo response;
    private final long timeTaken;
    private final LocalDateTime time;

    private Log(final String listenerId, final RequestInfo request, final ResponseInfo response, final long timeTaken, final LocalDateTime time) {
        this.listenerId = listenerId;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
        this.time = time;
    }

    public static Log of(final ListenerConfiguration config, final RequestContext requestContext, final long timeTaken) {
        return new Log(config.getId(), requestContext.getRequest(), requestContext.getResponse(), timeTaken, LocalDateTime.now());
    }

    public String getListenerId() {
        return listenerId;
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
