package org.goodiemania.hecate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.goodiemania.hecate.configuration.Configuration;
import org.goodiemania.hecate.configuration.ConfigurationFile;
import org.goodiemania.hecate.logs.Log;
import org.goodiemania.hecate.managers.admin.AdminManager;
import org.goodiemania.hecate.managers.listeners.ListenerManager;

public class MetaContext {
    private final ObjectMapper objectMapper;
    private final Configuration configuration;
    private final AdminManager adminManager;
    private final Map<String, List<Log>> logs = new HashMap<>();
    private final ConfigurationFile configurationFile;

    private Map<Integer, Javalin> javalinMap = new HashMap<>();

    public MetaContext(final String propsLocation) {
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();
        configurationFile = new ConfigurationFile(propsLocation, objectMapper);
        configuration = configurationFile.get();

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
                + "                                                                             \n");
    }

    public synchronized void reStart() {
        javalinMap.values().forEach(Javalin::stop);
        javalinMap = new HashMap<>();

        adminManager.start();

        configuration.getListeners().values()
                .forEach(listenerConfiguration -> ListenerManager.setUp(this, listenerConfiguration));
    }

    public void configUpdated() {
        configurationFile.update(configuration);
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
