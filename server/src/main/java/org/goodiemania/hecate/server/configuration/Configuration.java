package org.goodiemania.hecate.server.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private int adminPort;
    private Map<String, ListenerConfiguration> listeners = new HashMap<>();

    public Map<String, ListenerConfiguration> getListeners() {
        return listeners;
    }

    public void setListeners(final Map<String, ListenerConfiguration> listeners) {
        this.listeners = listeners;
    }

    public int getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(final int adminPort) {
        this.adminPort = adminPort;
    }
}
