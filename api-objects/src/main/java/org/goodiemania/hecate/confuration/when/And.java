package org.goodiemania.hecate.confuration.when;

import org.goodiemania.hecate.managers.listeners.RequestContext;

public class And extends When {
    private When when1;
    private When when2;

    public When getWhen1() {
        return when1;
    }

    public void setWhen1(final When when1) {
        this.when1 = when1;
    }

    public When getWhen2() {
        return when2;
    }

    public void setWhen2(final When when2) {
        this.when2 = when2;
    }

    @Override
    public boolean check(final RequestContext context) {
        return when1.check(context) && when2.check(context);
    }
}
