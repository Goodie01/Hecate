package org.goodiemania.hecate;

import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;

public class JavalinInstanceHolder {
    private Map<Integer, Javalin> javalinMap = new HashMap<>();

    public Javalin get(final int port) {
        Javalin javalin = javalinMap.get(port);

        if (javalin == null) {
            javalin = Javalin.create();
            javalin.config.showJavalinBanner = false;
            javalin.start(port);
            javalinMap.put(port, javalin);
        }

        return javalin;
    }

    public void stopAll() {
        javalinMap.values().forEach(Javalin::stop);
        javalinMap = new HashMap<>();
    }
}
