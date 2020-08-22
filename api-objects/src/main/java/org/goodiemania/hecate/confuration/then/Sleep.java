package org.goodiemania.hecate.confuration.then;

import org.goodiemania.hecate.managers.listeners.RequestContext;

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
