package org.goodiemania.hecate.server.listener;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import org.goodiemania.hecate.server.configuration.ListenerConfiguration;
import org.goodiemania.hecate.server.configuration.Rule;

public class ListenerContext {
    private Javalin listener;

    public ListenerContext(final MetaContext metaContext, final ListenerConfiguration listenerConfiguration) {
        listener = metaContext.get(listenerConfiguration.getPort());
        listener.addHandler(
                HandlerType.valueOf(listenerConfiguration.getHttpMethod()),
                listenerConfiguration.getContext(),
                ctx -> {
                    for (Rule rule : listenerConfiguration.getRules()) {
                        if (!rule.process(ctx)) {
                            break;
                        }
                    }
                });
    }

    public void close() {
        this.listener.stop();
    }
}
