package org.goodiemania.hecate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import org.goodiemania.hecate.configuration.Configuration;
import org.goodiemania.hecate.confuration.ListenerConfiguration;
import org.goodiemania.hecate.confuration.Rule;
import org.goodiemania.hecate.confuration.then.StaticReturn;
import org.goodiemania.hecate.confuration.when.And;
import org.goodiemania.hecate.confuration.when.BodyContains;
import org.goodiemania.hecate.confuration.when.PathContains;

public class MainConfigurationGenerator {
    public static void main(String[] args) {
        final ObjectMapper objectMapper = new ObjectMapper();

        final ListenerConfiguration listenerConfiguration = new ListenerConfiguration();
        listenerConfiguration.setId("bobbyDropTables");
        listenerConfiguration.setPort(8443);
        listenerConfiguration.setContext("/");
        listenerConfiguration.setHttpMethod("GET");

        final PathContains bodyContains = new PathContains();
        bodyContains.setSearchString("HelloThere");

        final StaticReturn staticReturn = new StaticReturn();
        staticReturn.setResponseCode(200);
        staticReturn.setStaticResponse("GeneralKonobi");

        final Rule rule0 = new Rule();
        rule0.setId(UUID.randomUUID().toString());
        rule0.setWhen(bodyContains);
        rule0.setThen(staticReturn);
        rule0.setOrder(0);

        final And and = new And();
        final BodyContains bodyContains1 = new BodyContains();
        bodyContains1.setSearchString("123456");
        final PathContains pathContains = new PathContains();
        pathContains.setSearchString("123456");
        and.setWhen1(bodyContains1);
        and.setWhen2(pathContains);

        final StaticReturn staticReturn1 = new StaticReturn();
        staticReturn1.setResponseCode(201);
        staticReturn1.setStaticResponse("The secret is here");

        final Rule rule1 = new Rule();
        rule1.setId(UUID.randomUUID().toString());
        rule1.setWhen(and);
        rule1.setThen(staticReturn1);
        rule1.setOrder(1);

        listenerConfiguration.setRules(Map.of(rule0.getId(), rule0, rule1.getId(), rule1));

        final Configuration configuration = new Configuration();
        configuration.setAdminPort(1234);
        configuration.getListeners().put(listenerConfiguration.getId(), listenerConfiguration);

        try {
            final String s = objectMapper.writeValueAsString(configuration);
            System.out.println(s);
            System.exit(0);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
