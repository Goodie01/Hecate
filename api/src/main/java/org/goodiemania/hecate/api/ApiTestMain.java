package org.goodiemania.hecate.api;

import org.goodiemania.hecate.confuration.ListenerConfiguration;

public class ApiTestMain {
    public static void main(String[] args) {
        final String path = "http://localhost:1234";

        HecateApi hecateApi = HecateApi.builder()
                .setBaseUri(path)
                .build();

        hecateApi.getAllListeners()
                .stream()
                .map(ListenerConfiguration::getId)
                .forEach(System.out::println);

        hecateApi.getListener("bobbyDropTables")
                .map(ListenerConfiguration::getId)
                .ifPresent(System.out::println);

        hecateApi.getAllLogs()
                .forEach(log -> System.out.println(log.getId() + ":" + log.getResponse().getBody()));

        final ListenerConfiguration listenerConfiguration = new ListenerConfiguration();
        listenerConfiguration.setId("howdy");
        listenerConfiguration.setContext("there");
        listenerConfiguration.setHttpMethod("GET");
        listenerConfiguration.setPort(80080);
        hecateApi.writeListener(listenerConfiguration);

        hecateApi.getListener("howdy")
                .map(ListenerConfiguration::getId)
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("Fuck this"));
    }
}
