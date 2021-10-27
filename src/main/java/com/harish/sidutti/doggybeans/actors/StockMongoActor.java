package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.Stock;
import com.harish.sidutti.doggybeans.repository.StockRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StockMongoActor extends UntypedAbstractActor {

    private final StockRepository stockRepository;

    public StockMongoActor(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Stock) {
            Mono<Stock> updatable = stockRepository.save((Stock) message);
        }
    }
}
