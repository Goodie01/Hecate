package org.goodiemania.hecate.server.configuration;

import java.util.List;

public class Configuration {
    private int adminPort;
    private List<ListenerConfiguration> listeners;

    public List<ListenerConfiguration> getListeners() {
        return listeners;
    }

    public void setListeners(final List<ListenerConfiguration> listeners) {
        this.listeners = listeners;
    }

    public int getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(final int adminPort) {
        this.adminPort = adminPort;
    }
}
