package org.goodiemania.hecate.server.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.goodiemania.hecate.server.configuration.Configuration;
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
            configuration.getListeners().stream()
                    .filter(config -> StringUtils.equals(config.getName(), ctx.pathParam("listenerName")))
                    .findFirst()
                    .ifPresent(config -> {
                        final Rule[] rules = ctx.bodyAsClass(Rule[].class);
                        config.setRules(Arrays.asList(rules));
                    });
        });
        javalin.get("/:listenerName", ctx -> {
            configuration.getListeners().stream()
                    .filter(config -> StringUtils.equals(config.getName(), ctx.pathParam("listenerName")))
                    .findFirst()
                    .ifPresent(config -> {
                        try {
                            ctx.result(metaContext.getObjectMapper().writeValueAsString(config));
                        } catch (JsonProcessingException e) {
                            throw new IllegalStateException(e);
                        }
                    });
        });
        //TODO create the rest of the ways to manage config on the fly
        //TODO do we want this to update the config file?

    }
}
