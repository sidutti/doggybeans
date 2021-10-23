package com.harish.sidutti.doggybeans.config;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.server.Route;
import akka.management.cluster.bootstrap.ClusterBootstrap;
import akka.management.javadsl.AkkaManagement;
import akka.stream.Materializer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static com.harish.sidutti.doggybeans.config.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Configuration
public class Configurations {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) throws UnknownHostException {
        Config baseConfig = ConfigFactory.load();
        String serverName = InetAddress.getLocalHost().getCanonicalHostName().toLowerCase(Locale.ROOT);
        String tcp = "akka.remote.artery.canonical.hostname = " + serverName;
        log.info("Initializing Config with this={}", tcp);
        Config remoteConfig = ConfigFactory.parseString(tcp);
        ActorSystem system = ActorSystem.create("doggybeans", remoteConfig.withFallback(baseConfig));
        SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        AkkaManagement.get(system).start();
        log.info("actor system and management initiated");
        ClusterBootstrap.get(system).start();
        log.info("Cluster bootstrap initiated");
        return system;
    }


    @Bean
    public Materializer materializer(ActorSystem system) {
        log.info("http ports are being listened to");
        Materializer mat = Materializer.matFromSystem(system);
        CompletionStage<ServerBinding> httpStage = Http.get(system)
                .newServerAt("0.0.0.0", 8081)
                .withMaterializer(mat)
                .bind(createRoute());
        httpStage
                .whenComplete((binding, failure) -> {
                    if (failure == null) {
                        system.log().info("HTTP server now listening at port 8081");
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
