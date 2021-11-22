package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.History;
import com.harish.sidutti.doggybeans.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HistoryMongoActor extends UntypedAbstractActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryMongoActor.class);
    private final HistoryRepository historyRepository;

    public HistoryMongoActor(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof History) {
            LOGGER.info("Message Received on HistoryMongoActor text={}", message);
            Mono<History> updatable = historyRepository.save((History) message);
            updatable.subscribe(e -> LOGGER.info(e.toString()));
        }
    }
}
