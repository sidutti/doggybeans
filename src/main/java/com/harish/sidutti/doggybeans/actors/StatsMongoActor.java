package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.Stats;
import com.harish.sidutti.doggybeans.repository.StatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatsMongoActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatsMongoActor.class);
    private final StatsRepository statsRepository;

    public StatsMongoActor(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Stats) {
            LOGGER.info("Message Received on StatsMongoActor text={}", message);
            Mono<Stats> updatable = statsRepository.save((Stats) message);
            updatable.subscribe(e -> LOGGER.info(e.toString()));
        }
    }
}
