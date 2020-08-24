package org.goodiemania.hecate;

import javax.annotation.PostConstruct;
import io.quarkus.scheduler.Scheduled;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ServerScheduler {
    @Inject
    private HecateManager manager;

    public ServerScheduler() {
        System.out.println("Ohboi");
    }

    @PostConstruct
    public void init() {
        System.out.println("Creating this guy");
    }

    @Scheduled(every = "10s")
    public void schedule() {
        manager.updateLogs();
    }
}
