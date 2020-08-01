package org.goodiemania.hecate.server.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import java.util.Arrays;
import java.util.Optional;
import org.goodiemania.hecate.server.configuration.Configuration;
import org.goodiemania.hecate.server.configuration.ListenerConfiguration;
import org.goodiemania.hecate.server.configuration.Rule;
import org.goodiemania.hecate.server.listener.MetaContext;

public class AdminManager {
    private final MetaContext metaContext;
    private final Configuration configuration;

    public AdminManager(final MetaContext metaContext, final Configuration configuration) {
        this.metaContext = metaContext;
        this.configuration = configuration;
    }

    public void start() {
        final Javalin javalin = metaContext.getJavalinInstance(configuration.getAdminPort());

        //TODO
        //  we need a better way to handle this
        //  The use case is that we need a external test to be able to add and then remove a rule on the fly
        javalin.put("/:listenerName/rules", ctx -> {
            Optional.ofNullable(configuration.getListeners().get(ctx.pathParam("listenerName")))
                    .ifPresent(config -> {
                        final Rule[] rules = ctx.bodyAsClass(Rule[].class);
                        config.setRules(Arrays.asList(rules));
                    });
        });
        javalin.get("/:listenerName", ctx -> {
            Optional.ofNullable(configuration.getListeners().get(ctx.pathParam("listenerName")))
                    .ifPresent(config -> {
                        try {
                            ctx.result(metaContext.getObjectMapper().writeValueAsString(config));
                        } catch (JsonProcessingException e) {
                            throw new IllegalStateException(e);
                        }
                    });
        });
        javalin.put("/:listenerName", ctx -> {
            final ListenerConfiguration listenerConfiguration = metaContext.getObjectMapper().readValue(ctx.body(), ListenerConfiguration.class);

            Optional.ofNullable(configuration.getListeners().get(ctx.pathParam("listenerName")))
                    .ifPresent(config -> {
                        ctx.result("");
                    });
        });
        //TODO create the rest of the ways to manage config on the fly
        //TODO do we want this to update the config file?

    }
}
