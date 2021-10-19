package com.harish.sidutti.doggybeans.web;

import akka.actor.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

public class HealthCheckInfo implements Supplier<CompletionStage<Boolean>> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ActorSystem system;
    public HealthCheckInfo(ActorSystem system) {
        log.info("Somethings are happening");
        this.system = system;
    }

    @Override
    public CompletionStage<Boolean> get() {
        log.info("DemoHealthCheck called");
        return CompletableFuture.completedFuture(true);
    }
}
