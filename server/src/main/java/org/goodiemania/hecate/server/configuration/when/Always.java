package org.goodiemania.hecate.server.configuration.when;

import io.javalin.http.Context;

public class Always extends When {
    @Override
    public boolean check(final Context context) {
        return true;
    }
}
