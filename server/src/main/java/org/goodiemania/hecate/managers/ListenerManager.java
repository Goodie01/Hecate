package org.goodiemania.hecate.managers;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import org.goodiemania.hecate.MetaContext;
import org.goodiemania.hecate.confuration.ListenerConfiguration;
import org.goodiemania.hecate.confuration.Rule;
import org.goodiemania.hecate.logs.Log;
import org.goodiemania.hecate.managers.listeners.RequestContext;

public class ListenerManager {
    private ListenerManager() {
    }

    public static void setUp(final MetaContext metaContext, final ListenerConfiguration listenerConfiguration) {
        final Javalin javalinListener = metaContext.getJavalinInstance(listenerConfiguration.getPort());

        javalinListener.addHandler(
                HandlerType.valueOf(listenerConfiguration.getHttpMethod()),
                listenerConfiguration.getContext(),
                ctx -> {
                    final String path = ctx.path();
                    final String httpMethod = ctx.method();
                    final String body = ctx.body();
                    final Map<String, String> headers = ctx.headerMap();

                    final RequestContext requestContext = RequestContext.of(path, httpMethod, body, headers);
                    long startTime = System.currentTimeMillis();

                    final ArrayList<Rule> rules = new ArrayList<>(listenerConfiguration.getRules().values());
                    rules.sort(Comparator.comparing(Rule::getOrder));

                    for (Rule rule : rules) {
                        if (!rule.process(requestContext)) {
                            break;
                        }
                    }

                    long timeTaken = System.currentTimeMillis() - startTime;
                    processResponse(ctx, requestContext);

                    metaContext.addLog(listenerConfiguration.getId(), Log.of(listenerConfiguration.getId(), requestContext, timeTaken));
                });
    }

    private static void processResponse(final io.javalin.http.Context ctx, final RequestContext requestContext) {
        if (requestContext.getResponse().getStatusCode() <= 0) {
            requestContext.getResponse().setStatusCode(418);
        }

        if (requestContext.getResponse().getBody() == null) {
            requestContext.getResponse().setBody("");
        }

        if (requestContext.getResponse().getHeaders() == null) {
            requestContext.getResponse().setHeaders(Collections.emptyMap());
        }

        requestContext.getResponse().getHeaders().forEach((key, values) -> values.forEach(value -> ctx.header(key, value)));
        ctx.result(requestContext.getResponse().getBody());
        ctx.status(requestContext.getResponse().getStatusCode());
    }
}
