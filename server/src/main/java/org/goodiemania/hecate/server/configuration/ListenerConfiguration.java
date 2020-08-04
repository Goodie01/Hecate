package org.goodiemania.hecate.server.configuration;

import java.util.Map;

public class ListenerConfiguration {
    private String id;

    private int port;
    private String httpMethod;
    private String context;
    private Map<String, Rule> rules;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getContext() {
        return context;
    }

    public void setContext(final String context) {
        this.context = context;
    }

    public Map<String, Rule> getRules() {
        return rules;
    }

    public void setRules(final Map<String, Rule> rules) {
        this.rules = rules;
    }
}
