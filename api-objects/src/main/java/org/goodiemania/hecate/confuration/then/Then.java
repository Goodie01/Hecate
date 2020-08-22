package org.goodiemania.hecate.confuration.then;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.goodiemania.hecate.managers.listeners.RequestContext;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StaticReturn.class)
})
public abstract class Then {
    protected String type;

    public Then() {
        this.type = this.getClass().getSimpleName();
    }

    /**
     * @param context Javalin context
     * @return indicates if the process should continue or not
     */
    public abstract boolean process(final RequestContext context);

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
