package org.goodiemania.hecate.server.configuration;

import io.javalin.http.Context;
import org.goodiemania.hecate.server.configuration.then.Then;
import org.goodiemania.hecate.server.configuration.when.When;

public class Rule {
    private When when;
    private Then then;
    private int order;

    /**
     * @param context Javalin context
     * @return returns true if the process should continue processing other rules after this
     */
    public boolean process(final Context context) {
        if (when.check(context)) {
            return then.doTheThing(context);
        }

        return true;
    }

    public When getWhen() {
        return when;
    }

    public void setWhen(final When when) {
        this.when = when;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    public Then getThen() {
        return then;
    }

    public void setThen(final Then then) {
        this.then = then;
    }
}
