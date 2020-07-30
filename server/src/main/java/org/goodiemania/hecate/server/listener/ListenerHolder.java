package org.goodiemania.hecate.server.listener;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import java.util.List;
import org.goodiemania.hecate.server.configuration.ListenerConfiguration;
import org.goodiemania.hecate.server.configuration.Rule;

public class ListenerHolder {
    private final MetaContext metaContext;
    private final ListenerConfiguration listenerConfiguration;
    private final Javalin javalinListener;

    public ListenerHolder(final MetaContext metaContext, final ListenerConfiguration listenerConfiguration) {
        this.metaContext = metaContext;
        this.listenerConfiguration = listenerConfiguration;
        javalinListener = metaContext.getJavalinInstance(listenerConfiguration.getPort());

        javalinListener.addHandler(
                HandlerType.valueOf(listenerConfiguration.getHttpMethod()),
                listenerConfiguration.getContext(),
                ctx -> {
                    final RequestContext requestContext = RequestContext.of(ctx);

                    for (Rule rule : listenerConfiguration.getRules()) {
                        if (!rule.process(requestContext)) {
                            break;
                        }
                    }

                    processResponse(ctx, requestContext);
                });
    }

    public void setUpRoles(final List<Rule> rules) {
        this.listenerConfiguration.setRules(rules);
    }

    private static void processResponse(final io.javalin.http.Context ctx, final RequestContext requestContext) {
        requestContext.getResponse().getHeaders().forEach((key, values) -> values.forEach(value -> ctx.header(key, value)));
        ctx.result(requestContext.getResponse().getBody());
        ctx.status(requestContext.getResponse().getStatusCode());
    }
}
