package org.goodiemania.hecate;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.goodiemania.hecate.api.HecateApi;
import org.goodiemania.hecate.logs.Log;
import org.primefaces.model.LazyDataModel;

@Named
@ApplicationScoped
public class HecateManager {
    private HecateApi hecateApi;
    private List<Log> logs;
    private Log selectedLog;

    @Inject
    @Push
    private PushContext push;

    @PostConstruct
    public void init() {
        final String hecateInstanceUri = "http://localhost:1234";
        hecateApi = HecateApi.builder().setBaseUri(hecateInstanceUri).build();
        logs = new ArrayList<>(hecateApi.getAllLogs());
    }

    public void updateLogs() {
        System.out.println("Making for call logs");
        logs = new ArrayList<>(hecateApi.getAllLogs());
        System.out.println("Found: " + logs.size());
        //push.send("UpdateLogs");
    }

    public List<Log> getLogs() {
        return logs;
    }

    public Log getSelectedLog() {
        return selectedLog;
    }

    public void setSelectedLog(final Log selectedLog) {
        this.selectedLog = selectedLog;
    }
}
