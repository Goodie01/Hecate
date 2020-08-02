package org.goodiemania.hecate.server.listener;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import org.goodiemania.hecate.server.MetaContext;
import org.goodiemania.hecate.server.configuration.ListenerConfiguration;
import org.goodiemania.hecate.server.configuration.Rule;

public class ListenerManager {
    private ListenerManager() {
    }

    public static void setUp(final MetaContext metaContext, final ListenerConfiguration listenerConfiguration) {
        final Javalin javalinListener = metaContext.getJavalinInstance(listenerConfiguration.getPort());

        javalinListener.addHandler(
                HandlerType.valueOf(listenerConfiguration.getHttpMethod()),
                listenerConfiguration.getContext(),
                ctx -> {
                    final RequestContext requestContext = RequestContext.of(ctx);
                    long startTime = System.currentTimeMillis();

                    for (Rule rule : listenerConfiguration.getRules().values()) {
                        if (!rule.process(requestContext)) {
                            break;
                        }
                    }

                    long timeTaken = System.currentTimeMillis() - startTime;
                    processResponse(ctx, requestContext);

                    metaContext.addLog(listenerConfiguration.getName(), Log.of(listenerConfiguration, requestContext, timeTaken));
                });
    }

    private static void processResponse(final io.javalin.http.Context ctx, final RequestContext requestContext) {
        requestContext.getResponse().getHeaders().forEach((key, values) -> values.forEach(value -> ctx.header(key, value)));
        ctx.result(requestContext.getResponse().getBody());
        ctx.status(requestContext.getResponse().getStatusCode());
    }
}
