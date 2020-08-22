package org.goodiemania.hecate.confuration.when;

import org.apache.commons.lang3.StringUtils;
import org.goodiemania.hecate.managers.listeners.RequestContext;

public class BodyContains extends When {
    private String searchString;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(final String searchString) {
        this.searchString = searchString;
    }

    @Override
    public boolean check(final RequestContext context) {
        return StringUtils.contains(context.getRequest().getBody(), searchString);
    }
}
