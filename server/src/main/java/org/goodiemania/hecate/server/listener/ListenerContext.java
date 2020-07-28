package org.goodiemania.hecate.server.listener;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import org.goodiemania.hecate.server.listener.configuration.Configuration;

public class ListenerContext {
    private MetaContext metaContext;
    private Javalin listener;
    private Configuration configuration;

    public ListenerContext(final MetaContext metaContext, final Configuration configuration) {
        this.metaContext = metaContext;
        this.configuration = configuration;

        listener = metaContext.get(configuration.getPort());
        listener.addHandler(HandlerType.valueOf(configuration.getHttpMethod()), configuration.getContext(), ctx -> ctx.result("Hello there"));
    }

    public void close() {
        this.listener.stop();
    }
}
