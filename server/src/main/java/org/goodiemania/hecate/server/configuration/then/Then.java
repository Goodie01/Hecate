package org.goodiemania.hecate.server.configuration.then;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.javalin.http.Context;

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
    public abstract boolean doTheThing(final Context context);

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
