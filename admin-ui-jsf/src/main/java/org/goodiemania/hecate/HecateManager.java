package org.goodiemania.hecate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.goodiemania.hecate.api.HecateApi;
import org.goodiemania.hecate.logs.Log;

@Named
@ApplicationScoped
public class HecateManager {
    private HecateApi hecateApi;
    private List<Log> logs = new ArrayList<>();
    private Log selectedLog;

    @Inject
    @Push
    private PushContext pushChannel;

    @PostConstruct
    public void init() {
        final String hecateInstanceUri = "http://localhost:1234";
        hecateApi = HecateApi.builder().setBaseUri(hecateInstanceUri).build();
        logs = new ArrayList<>(hecateApi.getAllLogs());
    }

    public void updateLogs() {
        logs = new ArrayList<>(hecateApi.getAllLogs());
        pushChannel.send("UpdateLogs");
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

    public List<HeaderValues> convertUiFriendlyHeaders(final Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(stringStringEntry -> new HeaderValues(stringStringEntry.getKey(), stringStringEntry.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }

    private static class HeaderValues {
        private final String key;
        private final String value;

        public HeaderValues(final String key, final String value) {

            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }
    }
}
