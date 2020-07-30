package org.goodiemania.hecate.server.configuration.when;

import org.apache.commons.lang3.StringUtils;
import org.goodiemania.hecate.server.listener.RequestContext;

public class PathContains extends When {
    private String searchString;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(final String searchString) {
        this.searchString = searchString;
    }

    @Override
    public boolean check(final RequestContext context) {
        return StringUtils.contains(context.getRequest().getPath(), searchString);
    }
}
