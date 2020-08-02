package org.goodiemania.hecate.server;

import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        final String props = System.getProperty("props");

        if (StringUtils.isEmpty(props)) {
            System.err.println("props system property empty");
            System.exit(1);
        }

        new MetaContext(props).reStart();
    }
}
