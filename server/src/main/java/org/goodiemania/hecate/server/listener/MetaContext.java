package org.goodiemania.hecate.server.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;

public class MetaContext {
    private final Map<Integer, Javalin> javalinMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Javalin getJavalinInstance(final int port) {
        Javalin javalin = javalinMap.get(port);

        if(javalin == null) {
            javalin = Javalin.create().start(port);
            javalinMap.put(port, javalin);
        }

        return javalin;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
