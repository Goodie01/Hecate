package org.goodiemania.hecate.confuration.when;

import org.goodiemania.hecate.managers.listeners.RequestContext;

public class Always extends When {
    @Override
    public boolean check(final RequestContext context) {
        return true;
    }
}
