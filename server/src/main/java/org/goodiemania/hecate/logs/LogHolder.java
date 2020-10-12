package org.goodiemania.hecate.logs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogHolder {
    private final Map<String, List<Log>> logs = new HashMap<>();

    public void addLog(final String listenerName, final Log log) {
        List<Log> logsList = logs.getOrDefault(listenerName, new ArrayList<>());
        logsList.add(log);
        logs.put(listenerName, logsList);
    }

    public List<Log> getAll() {
        return logs.values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Log::getTime))
                .collect(Collectors.toList());
    }

    public List<Log> getForSpecificListener(final String listenerName) {
        return logs.getOrDefault(listenerName, Collections.emptyList());
    }
}
