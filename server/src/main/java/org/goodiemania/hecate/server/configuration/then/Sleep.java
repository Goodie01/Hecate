package org.goodiemania.hecate.server.configuration.then;

import org.goodiemania.hecate.server.listener.RequestContext;

public class Sleep extends Then {
    private int millisecondsToSleep;

    @Override
    public boolean process(final RequestContext context) {
        try {
            Thread.sleep(millisecondsToSleep);
        } catch (InterruptedException e) {
            //suggestion from sonar
            Thread.currentThread().interrupt();
        }

        return true;
    }

    public int getMillisecondsToSleep() {
        return millisecondsToSleep;
    }

    public void setMillisecondsToSleep(final int millisecondsToSleep) {
        this.millisecondsToSleep = millisecondsToSleep;
    }
}
