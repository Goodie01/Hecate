package org.goodiemania.hecate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.javalin.Javalin;
import io.javalin.core.JavalinServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JavalinInstanceHolderTest {
    private final int port = 12345;
    private JavalinInstanceHolder javalinInstanceHolder;

    @BeforeEach
    void setUp() {
        javalinInstanceHolder = new JavalinInstanceHolder();
    }

    @AfterEach
    void tearDown() {
        javalinInstanceHolder.stopAll();
    }

    @Test
    void getReturnsRunningJavalinInstance() {
        final Javalin javalin = javalinInstanceHolder.get(port);
        assertNotNull(javalin);

        final JavalinServer server = javalin.server();
        assertNotNull(server);
        assertTrue(server.getStarted());
        assertEquals(port, server.getServerPort());
    }

    @Test
    void ensureAreGivenTheSameInstance() {
        final Javalin javalin = javalinInstanceHolder.get(port);
        final Javalin javalin2 = javalinInstanceHolder.get(port);

        assertEquals(javalin, javalin2);
    }

    /*
    This a round about way to ensure that stopAll stops all running instances, if the instance wasn't stopped we would get a different instance listening on the same port
     */
    @Test
    void ensureThatStopAllStopsInstance() {
        final Javalin javalin = javalinInstanceHolder.get(port);
        javalinInstanceHolder.stopAll();

        final Javalin secondJavalin = javalinInstanceHolder.get(port);
        assertNotEquals(javalin, secondJavalin);

        final JavalinServer server = javalin.server();
        final JavalinServer secondServer = secondJavalin.server();

        assertNotNull(server);
        assertNotNull(secondServer);

        assertEquals(server.getServerPort(), secondServer.getServerPort());
    }

}