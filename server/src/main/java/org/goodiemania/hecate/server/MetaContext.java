package org.goodiemania.hecate.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.goodiemania.hecate.server.admin.AdminManager;
import org.goodiemania.hecate.server.configuration.Configuration;
import org.goodiemania.hecate.server.listener.ListenerManager;
import org.goodiemania.hecate.server.listener.Log;

public class MetaContext {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Configuration configuration;
    private final String propsLocation;
    private final AdminManager adminManager;
    private final Map<String, List<Log>> logs = new HashMap<>();

    private Map<Integer, Javalin> javalinMap = new HashMap<>();

    public MetaContext(final String propsLocation) {
        this.propsLocation = propsLocation;

        try (final FileReader fileReader = new FileReader(this.propsLocation)) {
            configuration = objectMapper.readValue(fileReader, Configuration.class);
        } catch (IOException e) {
            System.err.println("Failed to load configuration");
            throw new IllegalStateException(e);
        }

        adminManager = new AdminManager(this, configuration);
    }

    public synchronized void reStart() {
        javalinMap.values().forEach(Javalin::stop);
        javalinMap = new HashMap<>();

        adminManager.start();

        configuration.getListeners().values()
                .forEach(listenerConfiguration -> ListenerManager.setUp(this, listenerConfiguration));
    }

    public void configUpdated() {
        try (final FileWriter fileWriter = new FileWriter(this.propsLocation)) {
            objectMapper.writeValue(fileWriter, configuration);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read config");
        }

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

    public synchronized Javalin getJavalinInstance(final int port) {
        Javalin javalin = javalinMap.get(port);

        if (javalin == null) {
            javalin = Javalin.create().start(port);
            javalinMap.put(port, javalin);
        }

        return javalin;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
