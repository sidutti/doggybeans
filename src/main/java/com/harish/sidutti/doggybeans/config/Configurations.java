package com.harish.sidutti.doggybeans.config;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.server.Route;
import akka.management.cluster.bootstrap.ClusterBootstrap;
import akka.management.javadsl.AkkaManagement;
import akka.stream.Materializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static com.harish.sidutti.doggybeans.config.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Configuration
public class Configurations {
    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) {
        ActorSystem system = ActorSystem.create("doggybeans");
        SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }

    @Bean
    public AkkaManagement akkaSystemManagement(ActorSystem system) {
        AkkaManagement akkaManagement = AkkaManagement.get(system);
        akkaManagement.start();
        return akkaManagement;
    }

    @Bean
    public ClusterBootstrap clusterManagement(ActorSystem system) {
        ClusterBootstrap clusterBootstrap = ClusterBootstrap.get(system);
        clusterBootstrap.start();
        return clusterBootstrap;
    }

    @Bean
    public Materializer materializer(ActorSystem system) {
        Materializer mat = Materializer.matFromSystem(system);
        CompletionStage<ServerBinding> httpStage = Http.get(system)
                .newServerAt("http", 8080)
                .withMaterializer(mat)
                .bind(createRoute());
        httpStage
                .whenComplete((binding, failure) -> {
                    if (failure == null) {
                        system.log().info("HTTP server now listening at port 8080");
                    } else {
                        system.log().error(failure, "Failed to bind HTTP server, terminating.");
                        system.terminate();
                    }
                });
        return mat;
    }

    private Route createRoute() {
        return concat(
                path("hello", () ->
                        get(() ->
                                complete("<h1>Say hello to akka-http</h1>"))));
    }
}
