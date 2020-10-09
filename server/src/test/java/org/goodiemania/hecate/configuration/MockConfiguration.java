package org.goodiemania.hecate.configuration;

public class MockConfiguration implements ConfigurationProvider{
    private final Configuration configuration;

    public MockConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration get() {
        return configuration;
    }

    @Override
    public void update(final Configuration configuration) {
        //no-op
    }
}
