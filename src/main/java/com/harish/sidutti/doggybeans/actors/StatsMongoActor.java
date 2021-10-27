package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.Stats;
import com.harish.sidutti.doggybeans.repository.StatsRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatsMongoActor extends UntypedAbstractActor {

    private final StatsRepository statsRepository;

    public StatsMongoActor(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Stats) {
            Mono<Stats> updatable = statsRepository.save((Stats) message);
        }
    }
}
