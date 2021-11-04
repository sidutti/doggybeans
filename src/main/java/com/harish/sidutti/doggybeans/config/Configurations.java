package com.harish.sidutti.doggybeans.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.metrics.AdaptiveLoadBalancingGroup;
import akka.cluster.metrics.MixMetricsSelector;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.server.Route;
import akka.management.cluster.bootstrap.ClusterBootstrap;
import akka.management.javadsl.AkkaManagement;
import akka.routing.Group;
import akka.stream.Materializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static com.harish.sidutti.doggybeans.config.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Configuration
public class Configurations {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) {
        ActorSystem system = ActorSystem.create("doggybeans");
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
    @Bean
    public ActorRef stockDataParsingActorRef(ActorSystem actorSystem){
        String actorName = "stockDataParsingActor";
        return createRoutes(actorSystem, actorName);
    }

    @Bean
    public ActorRef quoteMongoActorRef(ActorSystem actorSystem){
        String actorName = "quoteMongoActor";
        return createRoutes(actorSystem, actorName);
    }

    @Bean
    public ActorRef stockMongoActorRef(ActorSystem actorSystem) {
        String actorName = "stockMongoActor";
        return createRoutes(actorSystem, actorName);
    }

    @Bean
    public ActorRef statsMongoActorRef(ActorSystem actorSystem) {
        String actorName = "statsMongoActor";
        return createRoutes(actorSystem, actorName);
    }
    @Bean
    public ActorRef historyMongoActorRef(ActorSystem actorSystem) {
        String actorName = "historyMongoActor";
        return createRoutes(actorSystem, actorName);
    }
    @Bean
    public ActorRef metadataActorRef(ActorSystem actorSystem) {
        String actorName = "metadataActor";
        return createRoutes(actorSystem, actorName);
    }

    @Bean
    public ActorRef historyProcessActorRef(ActorSystem actorSystem) {
        String actorName = "historyProcess";
        return createRoutes(actorSystem, actorName);
    }

    private ActorRef createRoutes(ActorSystem actorSystem, String actorName) {
        List<String> routes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String tempName = actorName + i;
            actorSystem.actorOf(SPRING_EXTENSION_PROVIDER.get(actorSystem).props(actorName), tempName);
            routes.add("/user/" + tempName);
        }
        Set<String> userRole = new HashSet<>(Collections.singletonList("compute"));
        Group group = new AdaptiveLoadBalancingGroup(MixMetricsSelector.getInstance(), routes);
        ClusterRouterGroupSettings routerSettings = new ClusterRouterGroupSettings(100000, routes, true, userRole);
        Props props = new ClusterRouterGroup(group,routerSettings).props();
        return actorSystem.actorOf(props,actorName+"Router");
    }
}
