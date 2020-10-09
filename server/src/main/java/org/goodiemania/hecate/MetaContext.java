package org.goodiemania.hecate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.goodiemania.hecate.configuration.Configuration;
import org.goodiemania.hecate.configuration.ConfigurationFile;
import org.goodiemania.hecate.configuration.ConfigurationProvider;
import org.goodiemania.hecate.confuration.ListenerConfiguration;
import org.goodiemania.hecate.logs.Log;
import org.goodiemania.hecate.managers.AdminManager;
import org.goodiemania.hecate.managers.ListenerHandler;

public class MetaContext {
    private final ObjectMapper objectMapper;
    private final Configuration configuration;
    private final AdminManager adminManager;
    private final JavalinInstanceHolder instanceHolder;

    private final Map<String, List<Log>> logs;

    private final ConfigurationProvider configurationProvider;

    public MetaContext(final ObjectMapper objectMapper, final ConfigurationProvider configurationProvider) {
        this.objectMapper = objectMapper;
        this.configurationProvider = configurationProvider;

        this.logs = new HashMap<>();
        this.instanceHolder = new JavalinInstanceHolder();

        this.configuration = this.configurationProvider.get();
        this.adminManager = new AdminManager(this, this.configuration);

        System.out.println(""
                + "  _    _                _       \n"
                + " | |  | |              | |      \n"
                + " | |__| | ___  ___ __ _| |_ ___ \n"
                + " |  __  |/ _ \\/ __/ _` | __/ _ \\\n"
                + " | |  | |  __/ (_| (_| | ||  __/\n"
                + " |_|  |_|\\___|\\___\\__,_|\\__\\___|\n"
                + "                                \n"
                + "                                \n"
                + "\n");
    }

    public synchronized void reStart() {
        instanceHolder.stopAll();

        adminManager.start();

        configuration.getListeners().values()
                .forEach(this::addHandler);
    }

    private void addHandler(final ListenerConfiguration listenerConfiguration) {
        instanceHolder.get(listenerConfiguration.getPort())
                .addHandler(
                        HandlerType.valueOf(listenerConfiguration.getHttpMethod()),
                        listenerConfiguration.getContext(),
                        new ListenerHandler(listenerConfiguration, this));
    }

    public void configUpdated() {
        configurationProvider.update(configuration);
        reStart();
    }

    public void addLog(final String listenerName, final Log log) {
        List<Log> logsList = logs.getOrDefault(listenerName, new ArrayList<>());
        logsList.add(log);
        logs.put(listenerName, logsList);
    }

    public Map<String, List<Log>> getLogs() {
        return logs;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public JavalinInstanceHolder getInstanceHolder() {
        return instanceHolder;
    }
}
