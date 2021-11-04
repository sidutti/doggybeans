package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.History;
import com.harish.sidutti.doggybeans.repository.HistoryRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HistoryMongoActor extends UntypedAbstractActor {

    private final HistoryRepository historyRepository;

    public HistoryMongoActor(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void onReceive(Object message)  {
        if(message instanceof History){
            Mono<History> updatable = historyRepository.save((History) message);
        }
    }
}
