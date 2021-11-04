package com.harish.sidutti.doggybeans.actors;

import akka.actor.UntypedAbstractActor;
import com.harish.sidutti.doggybeans.dto.Stock;
import com.harish.sidutti.doggybeans.repository.StocksRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StockMongoActor extends UntypedAbstractActor {

    private final StocksRepository stocksRepository;

    public StockMongoActor(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Stock) {
            Mono<Stock> updatable = stocksRepository.save((Stock) message);
        }
    }
}
