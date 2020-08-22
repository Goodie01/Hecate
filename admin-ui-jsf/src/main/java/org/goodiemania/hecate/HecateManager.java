package org.goodiemania.hecate;

import org.goodiemania.hecate.api.HecateApi;

public class HecateManager {
    public HecateManager() {
        final String hecateInstanceUri = "http://localhost:1234";
        HecateApi hecateApi = HecateApi.builder().setBaseUri(hecateInstanceUri).build();
    }
}
