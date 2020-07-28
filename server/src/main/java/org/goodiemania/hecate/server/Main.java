package org.goodiemania.hecate.server;

import org.goodiemania.hecate.server.listener.configuration.Configuration;
import org.goodiemania.hecate.server.listener.ListenerContext;
import org.goodiemania.hecate.server.listener.MetaContext;

public class Main {
    public static void main(String[] args) {
        final MetaContext metaContext = new MetaContext();

        final Configuration configuration = new Configuration();
        configuration.setName("bobbyDropTables");
        configuration.setPort(8443);
        configuration.setContext("/");
        configuration.setHttpMethod("GET");

        final ListenerContext listenerContext = new ListenerContext(metaContext, configuration);
    }
}
