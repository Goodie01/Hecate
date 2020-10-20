package org.goodiemania.hecate.api;

import java.util.List;
import java.util.Optional;
import org.goodiemania.hecate.confuration.ListenerConfiguration;
import org.goodiemania.hecate.confuration.Rule;
import org.goodiemania.hecate.logs.Log;

public interface HecateApi {
    String checkHealthCheck();
    List<ListenerConfiguration> getAllListeners();
    Optional<ListenerConfiguration> getListener(final String listenerId);
    void writeListener(final ListenerConfiguration configuration);
    void deleteListener(final String listenerId);

    void writeRule(final String listenerId, final Rule rule);
    void deleteRule(final String listenerId, final String ruleId);

    List<Log> getAllLogs();
    List<Log> getAllLogsForListener(final String listenerId);
    void deleteLogsForListener(final String listenerId);

    static HecateApiBuilder builder() {
        return new HecateApiBuilder();
    }
}
