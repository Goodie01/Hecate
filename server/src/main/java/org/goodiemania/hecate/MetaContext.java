package org.goodiemania.hecate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.goodiemania.hecate.logs.Log;
import org.goodiemania.hecate.managers.admin.AdminManager;
import org.goodiemania.hecate.configuration.Configuration;
import org.goodiemania.hecate.managers.listeners.ListenerManager;

public class MetaContext {
    private final ObjectMapper objectMapper;
    private final Configuration configuration;
    private final String propsLocation;
    private final AdminManager adminManager;
    private final Map<String, List<Log>> logs = new HashMap<>();

    private Map<Integer, Javalin> javalinMap = new HashMap<>();

    public MetaContext(final String propsLocation) {
        this.propsLocation = propsLocation;

        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();

        try (final FileReader fileReader = new FileReader(this.propsLocation)) {
            configuration = objectMapper.readValue(fileReader, Configuration.class);
        } catch (IOException e) {
            System.err.println("Failed to load configuration");
            throw new IllegalStateException(e);
        }

        adminManager = new AdminManager(this, configuration);

        System.out.println(""
                + "   ▄█    █▄       ▄████████  ▄████████    ▄████████     ███        ▄████████ \n"
                + "  ███    ███     ███    ███ ███    ███   ███    ███ ▀█████████▄   ███    ███ \n"
                + "  ███    ███     ███    █▀  ███    █▀    ███    ███    ▀███▀▀██   ███    █▀  \n"
                + " ▄███▄▄▄▄███▄▄  ▄███▄▄▄     ███          ███    ███     ███   ▀  ▄███▄▄▄     \n"
                + "▀▀███▀▀▀▀███▀  ▀▀███▀▀▀     ███        ▀███████████     ███     ▀▀███▀▀▀     \n"
                + "  ███    ███     ███    █▄  ███    █▄    ███    ███     ███       ███    █▄  \n"
                + "  ███    ███     ███    ███ ███    ███   ███    ███     ███       ███    ███ \n"
                + "  ███    █▀      ██████████ ████████▀    ███    █▀     ▄████▀     ██████████ \n"
                + "                                                                             \n"
                + "Welcome to the Hecate testing server");
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
            javalin = Javalin.create();
            javalin.config.showJavalinBanner = false;
            javalin.start(port);
            javalinMap.put(port, javalin);
        }

        return javalin;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
