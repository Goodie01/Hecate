package org.goodiemania.hecate.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.goodiemania.hecate.MetaContext;
import org.goodiemania.hecate.configuration.Configuration;
import org.goodiemania.hecate.confuration.ListenerConfiguration;
import org.goodiemania.hecate.confuration.Rule;
import org.goodiemania.hecate.logs.Log;

public class AdminManager {
    private static final String CORS_HEADER_NAME = "Access-Control-Allow-Origin";
    private final MetaContext metaContext;
    private final Configuration configuration;

    public AdminManager(final MetaContext metaContext, final Configuration configuration) {
        this.metaContext = metaContext;
        this.configuration = configuration;
    }

    public void start() {
        final Javalin javalin = metaContext.getInstanceHolder().get(configuration.getAdminPort());

        javalin.get("configuration/", this::getAllListeners);

        javalin.get("configuration/:listenerName", this::getListener);
        javalin.put("configuration/:listenerName", this::writeListener);
        javalin.delete("configuration/:listenerName", this::deleteListener);

        javalin.put("configuration/:listenerName/rules/:ruleId", this::writeRule);
        javalin.delete("configuration/:listenerName/rules/:ruleId", this::deleteRule);

        javalin.get("logs/", this::getAllLogs);
        javalin.get("logs/:listenerName", this::getLogs);
        javalin.delete("logs/:listenerName", this::deleteLogs);
    }

    private void getAllListeners(final Context ctx) throws JsonProcessingException {
        ctx.result(metaContext.getObjectMapper().writeValueAsString(configuration.getListeners().values()));
        ctx.header("Access-Control-Allow-Origin", "*");
    }

    private void getLogs(final Context ctx) {
        List<Log> logsList = metaContext.getLogsHolder().getForSpecificListener(ctx.pathParam("listenerName"));

        try {
            ctx.result(metaContext.getObjectMapper().writeValueAsString(logsList));
            ctx.header("Access-Control-Allow-Origin", "*");
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private void getAllLogs(final Context ctx) throws JsonProcessingException {
        List<Log> logsList = metaContext.getLogsHolder().getAll();

        try {
            ctx.result(metaContext.getObjectMapper().writeValueAsString(logsList));
            ctx.header("Access-Control-Allow-Origin", "*");
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private void deleteLogs(final Context ctx) {
        metaContext.getLogsHolder().getForSpecificListener(ctx.pathParam("listenerName"));
        ctx.result("Cleared logs");
        ctx.header(CORS_HEADER_NAME, "*");
    }

    private void deleteListener(final Context ctx) {
        configuration.getListeners().remove(ctx.pathParam("listenerName"));
        metaContext.configUpdated();
        ctx.result("Removed listener");
        ctx.header("Access-Control-Allow-Origin", "*");
    }

    private void writeListener(final Context ctx) {
        final ListenerConfiguration newConfiguration = ctx.bodyAsClass(ListenerConfiguration.class);

        if (!StringUtils.equals(ctx.pathParam("listenerName"), newConfiguration.getId())) {
            throw new IllegalStateException("listener name does not equal given listener name");
        }

        configuration.getListeners().put(newConfiguration.getId(), newConfiguration);
        metaContext.configUpdated();
        ctx.status(201);
        ctx.header("Access-Control-Allow-Origin", "*");
    }

    private void getListener(final Context ctx) {
        final String listenerNameFormPathParam = ctx.pathParam("listenerName");

        final Optional<ListenerConfiguration> configuration =
                Optional.ofNullable(this.configuration.getListeners().get(listenerNameFormPathParam));

        try {
            ctx.result(metaContext.getObjectMapper().writeValueAsString(configuration));
            ctx.header("Access-Control-Allow-Origin", "*");
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private void deleteRule(final Context ctx) {
        Optional.ofNullable(configuration.getListeners().get(ctx.pathParam("listenerName")))
                .ifPresent(config -> {
                    Optional.ofNullable(config.getRules().get(ctx.pathParam("ruleId")))
                            .ifPresent(oldRule -> {
                                config.getRules().remove(oldRule.getId());
                                ctx.result("removed rule");
                                ctx.header("Access-Control-Allow-Origin", "*");
                            });
                });
    }

    private void writeRule(final Context ctx) {
        Optional.ofNullable(configuration.getListeners().get(ctx.pathParam("listenerName")))
                .ifPresent(config -> {
                    final Rule newRule = ctx.bodyAsClass(Rule.class);

                    Optional.ofNullable(config.getRules().get(ctx.pathParam("ruleId")))
                            .ifPresent(oldRule -> {
                                config.getRules().put(newRule.getId(), newRule);
                                ctx.result("updated rule");
                                ctx.header("Access-Control-Allow-Origin", "*");
                            });
                });
    }
}
