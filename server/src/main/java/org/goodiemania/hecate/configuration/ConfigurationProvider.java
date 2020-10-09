package org.goodiemania.hecate.configuration;

public interface ConfigurationProvider {
    Configuration get();

    void update(Configuration configuration);
}
