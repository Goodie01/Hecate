package org.goodiemania.hecate.server.listener;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import org.goodiemania.hecate.server.configuration.ListenerConfiguration;

public class Log {
    public final String listenerId;
    private final RequestInfo request;
    private final ResponseInfo response;
    private final long timeTaken;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS", timezone = "UTC")
    private final ZonedDateTime time;

    private Log(final String listenerId, final RequestInfo request, final ResponseInfo response, final long timeTaken, final ZonedDateTime time) {
        this.listenerId = listenerId;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
        this.time = time;
    }

    public static Log of(final ListenerConfiguration config, final RequestContext requestContext, final long timeTaken) {
        return new Log(config.getId(), requestContext.getRequest(), requestContext.getResponse(), timeTaken, ZonedDateTime.now());
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

    public ZonedDateTime getTime() {
        return time;
    }
}
