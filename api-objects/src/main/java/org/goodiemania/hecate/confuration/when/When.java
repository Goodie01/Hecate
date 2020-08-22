package org.goodiemania.hecate.confuration.when;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.goodiemania.hecate.managers.listeners.RequestContext;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BodyContains.class),
        @JsonSubTypes.Type(value = BodyContainsIgnoreCase.class),
        @JsonSubTypes.Type(value = PathContains.class),
        @JsonSubTypes.Type(value = PathContainsIgnoreCase.class),
        @JsonSubTypes.Type(value = Or.class),
        @JsonSubTypes.Type(value = And.class),
        @JsonSubTypes.Type(value = Always.class)
})
public abstract class When {
    protected String type;

    public When() {
        this.type = this.getClass().getSimpleName();
    }

    public abstract boolean check(final RequestContext context);

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
