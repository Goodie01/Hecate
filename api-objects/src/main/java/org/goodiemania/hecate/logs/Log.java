package org.goodiemania.hecate.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.goodiemania.hecate.managers.listeners.RequestContext;
import org.goodiemania.hecate.managers.listeners.RequestInfo;
import org.goodiemania.hecate.managers.listeners.ResponseInfo;

public class Log {
    private String id;
    public String listenerId;
    private RequestInfo request;
    private ResponseInfo response;
    private long timeTaken;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS", timezone = "UTC")
    private ZonedDateTime time;

    public Log() {
        //no-op public constructor for Jackson
    }

    private Log(final String id,
                final String listenerId,
                final RequestInfo request,
                final ResponseInfo response,
                final long timeTaken,
                final ZonedDateTime time) {
        this.id = id;
        this.listenerId = listenerId;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
        this.time = time;
    }

    public static Log of(final String listenerId,
                         final RequestContext requestContext,
                         final long timeTaken) {
        return new Log(UUID.randomUUID().toString(),
                listenerId,
                requestContext.getRequest(),
                requestContext.getResponse(),
                timeTaken,
                ZonedDateTime.now());
    }

    public String getId() {
        return id;
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

    public void setId(final String id) {
        this.id = id;
    }

    public void setListenerId(final String listenerId) {
        this.listenerId = listenerId;
    }

    public void setRequest(final RequestInfo request) {
        this.request = request;
    }

    public void setResponse(final ResponseInfo response) {
        this.response = response;
    }

    public void setTimeTaken(final long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setTime(final ZonedDateTime time) {
        this.time = time;
    }
}
