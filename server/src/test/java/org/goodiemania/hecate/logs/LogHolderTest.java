package org.goodiemania.hecate.logs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.goodiemania.hecate.managers.listeners.RequestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogHolderTest {

    private LogHolder logHolder;

    @BeforeEach
    void setUp() {
        logHolder = new LogHolder();
    }

    @Test
    void new_log_holder_is_empty() {
        assertTrue(logHolder.getAll().isEmpty());
    }

    @Test
    void log_added_can_be_found() {
        RequestContext requestContext = RequestContext.of("path", "GET", "This is a body", Collections.emptyMap());
        Log firstListener = Log.of("first_listener", requestContext, 500);
        String firstListenerId = firstListener.getId();
        logHolder.addLog("first_listener", firstListener);

        List<Log> logList = logHolder.getAll();
        assertEquals(1, logList.size());
        Log log = logList.get(0);
        assertEquals("first_listener", log.getListenerId());
        assertEquals(firstListenerId, log.getId());
    }

}