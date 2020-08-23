package org.goodiemania.hecate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.goodiemania.hecate.api.HecateApi;

@Named
@ApplicationScoped
public class HecateManager {
    private HecateApi hecateApi;

    @PostConstruct
    public void init() {
        final String hecateInstanceUri = "http://localhost:1234";
        hecateApi = HecateApi.builder().setBaseUri(hecateInstanceUri).build();
    }
}
