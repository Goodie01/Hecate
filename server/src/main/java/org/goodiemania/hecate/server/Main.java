package org.goodiemania.hecate.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.goodiemania.hecate.server.admin.AdminManager;
import org.goodiemania.hecate.server.configuration.Configuration;
import org.goodiemania.hecate.server.listener.ListenerHolder;
import org.goodiemania.hecate.server.listener.MetaContext;

public class Main {
    public static void main(String[] args) {
        final MetaContext metaContext = new MetaContext();
        final String props = System.getProperty("props");

        if (StringUtils.isEmpty(props)) {
            System.err.println("props system property empty");
            System.exit(1);
        }

        Configuration configuration = null;

        try (final FileReader fileReader = new FileReader(props)) {
            configuration = metaContext.getObjectMapper().readValue(fileReader, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (configuration == null) {
            System.err.println("Failed to load configuration");
            System.exit(2);
        }

        final AdminManager adminManager = new AdminManager(metaContext, configuration);

        final ListenerHolder listenerHolder = new ListenerHolder(metaContext, configuration.getListeners().get(0));
    }
}
