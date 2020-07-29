package org.goodiemania.hecate.server.configuration.when;

import io.javalin.http.Context;
import org.apache.commons.lang3.StringUtils;

public class BodyContainsIgnoreCase extends When {
    private String searchString;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(final String searchString) {
        this.searchString = searchString;
    }

    @Override
    public boolean check(final Context context) {
        return StringUtils.contains(context.body(), searchString);
    }
}
