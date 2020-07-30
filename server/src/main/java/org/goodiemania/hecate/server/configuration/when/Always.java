package org.goodiemania.hecate.server.configuration.when;

import org.goodiemania.hecate.server.listener.RequestContext;

public class Always extends When {
    @Override
    public boolean check(final RequestContext context) {
        return true;
    }
}
