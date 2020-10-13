package org.goodiemania.hecate.managers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.goodiemania.hecate.MetaContext;
import org.goodiemania.hecate.confuration.ListenerConfiguration;
import org.goodiemania.hecate.confuration.Rule;
import org.goodiemania.hecate.logs.Log;
import org.goodiemania.hecate.managers.listeners.RequestContext;
import org.goodiemania.hecate.managers.listeners.ResponseInfo;
import org.jetbrains.annotations.NotNull;

public class ListenerHandler implements Handler {
    private final ListenerConfiguration listenerConfiguration;
    private final MetaContext metaContext;

    public ListenerHandler(final ListenerConfiguration listenerConfiguration, final MetaContext metaContext) {
        this.listenerConfiguration = listenerConfiguration;
        this.metaContext = metaContext;
    }

    @Override
    public void handle(@NotNull final Context ctx) throws Exception {
        final RequestContext requestContext = RequestContext.of(ctx.path(),
                ctx.method(),
                ctx.body(),
                ctx.headerMap());

        long timeTaken = timeEvent(() -> processRules(requestContext));

        processResponse(ctx, requestContext);

        metaContext.getLogsHolder().addLog(listenerConfiguration.getId(), Log.of(listenerConfiguration.getId(), requestContext, timeTaken));
    }

    private static long timeEvent(final Runnable runnable) {
        long startTime = System.currentTimeMillis();

        runnable.run();

        return System.currentTimeMillis() - startTime;
    }

    private void processRules(final RequestContext requestContext) {
        final ArrayList<Rule> sortRules = getSortRules();

        if (sortRules.isEmpty()) {
            createEmptyResponse(requestContext);
            return;
        }

        for (Rule rule : sortRules) {
            if (!rule.process(requestContext)) {
                break;
            }
        }
    }

    private void createEmptyResponse(final RequestContext requestContext) {
        final ResponseInfo response = requestContext.getResponse();
        response.setBody("{\"Message\":\"No rules found for listener\"}");
        response.setStatusCode(418);
    }

    @NotNull
    private ArrayList<Rule> getSortRules() {
        final ArrayList<Rule> rules = new ArrayList<>(listenerConfiguration.getRules().values());
        rules.sort(Comparator.comparing(Rule::getOrder));
        return rules;
    }

    private static void processResponse(final Context ctx, final RequestContext requestContext) {
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
