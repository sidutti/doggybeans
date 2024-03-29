package com.harish.sidutti.doggybeans.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

public class HealthCheckInfo implements Supplier<CompletionStage<Boolean>> {
        private final Logger log = LoggerFactory.getLogger(getClass());


        @Override
        public CompletionStage<Boolean> get() {
                log.info("DemoHealthCheck called");
                return CompletableFuture.completedFuture(true);
        }
}
