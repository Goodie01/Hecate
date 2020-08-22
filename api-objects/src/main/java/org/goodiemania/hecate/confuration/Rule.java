package org.goodiemania.hecate.confuration;


import org.goodiemania.hecate.confuration.then.Then;
import org.goodiemania.hecate.confuration.when.When;
import org.goodiemania.hecate.managers.listeners.RequestContext;

public class Rule {
    private String id;
    private When when;
    private Then then;
    private int order;

    /**
     * @param context Javalin context
     * @return returns true if the process should continue processing other rules after this
     */
    public boolean process(final RequestContext context) {
        if (when.check(context)) {
            return then.process(context);
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

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
